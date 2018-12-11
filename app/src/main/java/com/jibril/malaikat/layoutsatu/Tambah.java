package com.jibril.malaikat.layoutsatu;


//Source Code untuk memberikan platform di code di bawahnya
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


//public class yaitu sebuah class yang bisa di panggil secara public atau bisa di panggil di tempat lain sesuai nama class publicnya
public class Tambah extends AppCompatActivity {

    //penamaan object

    EditText display, editText;
    ListView lv;
    ArrayAdapter<String> adapter;
    Button addButton;
    AlertDialog.Builder dialog;
    View dialogView;
    LayoutInflater inflater;


    //isi dalam class yaitu seperti code untuk menjalan kan sesuai yang kita inginkan
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //untuk memberikan lokasi di mana layout berada dan mananya apa atau bisa di sebut penghubung antara java dan tampilan atau layout
        setContentView(R.layout.tambah);

        //membrikan isi dari object yang di atas
        //kalo untuk yang di bawah adalah untuk memberikan nama sesuai letak dari jalannya depan program
        //untuk inisialisasi id di layout
        display = findViewById(R.id.editText1);
        lv = findViewById(R.id.listView1);
        addButton = findViewById(R.id.button1);

        //untuk menampung data
        //adapter adalah jembatan untuk sebuah array yang memiliki data dan data tersebut di kirim ke layout depan
        //listview adalah untuk menerima semua data yang ada di array adapter untuk di tampilkan berbentuk list
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        //set data di listview
        lv.setAdapter(adapter);

        //lv.set dll iitu untuk agar si listview pas diklik itu apa yang akan terjadi
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //ini adalah sebuah alaur atau logika untuk jalannya bagian listview yang di gabung di sini
            //biar dia gak mencar" gak pisah, takutnya kalo pisah rindu lagi kek dilan
            //intinya biar logikanya berjalan di sini di satuin
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int posisi, long l) {

                //final adalah agar kita tidak bisa melakukan overide pada methd ini
                //agar tidak bisa memanipulasi data lagi
                final String data = adapterView.getItemAtPosition(posisi).toString();


                //untuk memberikan alert dialog ke mainactivity
                //this adalah untuk menyatakan objek pada suatu class
                dialog = new AlertDialog.Builder(Tambah.this);

                //mengambil layout lain
                inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.data_update, null);

                //untuk mensetting alert di keluar di listview
                dialog.setView(dialogView);

                //jadi pada saat di sentuh di luar alert maka alert akan otomatis tercancel
                dialog.setCancelable(true);

                //memberikan inisialisasi id di object
                editText = dialogView.findViewById(R.id.etUpdate);

                //mengambil data yang ada di string final
                editText.setText(data);

                //memberikan alaert dialog ataupun pop up update
                dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {

                    //penamaan sebuah class
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //karena di sini hanya menggunaka perbandingan makanya dia tidak menggunakan final
                        //karena dia hanya di butuhkan di sini saja
                        //dan dia harus di overide
                        String update_data = editText.getText().toString();

                        //jika banyak data sama dengan 0 berarti isi data kosong berada di pop upnya
                        //maka dia akan memberikan peringatan joka data itu 0
                        //ataupun ata itu kosong
                        if (update_data.length() <= 0) {
                            Toast.makeText(Tambah.this, "data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                        }

                        //jika data tidak kosong atau lebih dari 2 khuruf maka data akan di eksekusi dan datapun bersil terupdate
                        else {

                            //untuk memberikan pesan kepada pengguna jika data yang dia update sudah selesai
                            //dan akan tampin di layout mainactivity karena main activity sudah di jadikan objek di
                            //perbandingan di bawah ini
                            Toast.makeText(Tambah.this, "update sukses", Toast.LENGTH_SHORT).show();

                            //update data
                            adapter.insert(update_data, posisi);
                            adapter.notifyDataSetChanged();
                            SavePreferences("LISTS", update_data);

                            //replace data
                            adapter.remove(data);
                            adapter.notifyDataSetChanged();
                            SavePreferences("LISTS", data);
                        }
                    }
                });


                //memberikan perinta untuk menghapus isi dari array
                dialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //jika di array datanya tidak ada ataupun kosong maka dia akan memberi pesen bahwa data kosong
                        if (data.isEmpty()) {
                            Toast.makeText(Tambah.this, "data kosong", Toast.LENGTH_SHORT).show();
                        }

                        //memberikan pesan jika data telah berhasil di hapus
                        else {

                            //yaitu pesan yang keluar di layout mainactivity
                            //karena activity sudah di jadikan objek di sini untuk tempat pesan keluar di layaout mana
                            Toast.makeText(Tambah.this, "delete sukses", Toast.LENGTH_SHORT).show();
                            adapter.remove(data);
                            adapter.notifyDataSetChanged();
                            SavePreferences("LISTS", data);
                        }
                    }
                });

                //untuk menutup atau membatalkan perubahan yang akan kita lakukan
                dialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Tambah.this, "cancel", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                });

                //untuk mengeluar kan atau memperlihatkan setiap dialog
                dialog.show();
            }
        });

        //untuk tempat penyimpanan dari array

        LoadPreferences();

        //untuk pada saat button di klik maka dia akan memasukkan ke array yang ada di dalam database share preference
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String task = display.getText().toString();

                //umemberi tahu bahwa data yang akan di input tidak boleh kosong
                if (task.isEmpty()) {
                    Toast.makeText(Tambah.this, "Data tidak boleh kosong..!!", Toast.LENGTH_LONG).show();
                }

                //jika data tidak kosong maka data akan di masukkan di array penyimpanan
                else {
                    adapter.add(task);
                    adapter.notifyDataSetChanged();
                    SavePreferences("LISTS", task);
                }
            }
        });
    }


    //Menyimpan data
    protected void SavePreferences(String key, String value) {
        // TODO Auto-generated method stub
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = data.edit();
        editor.putString(key, value);
        editor.apply();
    }

    //meload data ke layout agar dapat tampil di list view
    protected void LoadPreferences() {
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        String dataSet = data.getString("LISTS", "None Available");
        adapter.add(dataSet);
        adapter.notifyDataSetChanged();
    }
}