package dev.cisnux.myreadwritefile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import dev.cisnux.myreadwritefile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            buttonNew.setOnClickListener(this@MainActivity)
            buttonOpen.setOnClickListener(this@MainActivity)
            buttonSave.setOnClickListener(this@MainActivity)
        }
    }

    private fun newFile() {
        with(binding) {
            editTitle.setText("")
            editFile.setText("")
            Toast.makeText(this@MainActivity, "Clearing file", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_new -> newFile()
            R.id.button_open -> showList()
            R.id.button_save -> saveFile()
        }
    }

    private fun saveFile() {
        when {
            binding.editTitle.text.toString().isEmpty() -> Toast.makeText(
                this,
                "Title harus diisi terlebih dahulu",
                Toast.LENGTH_SHORT
            ).show()
            binding.editFile.text.toString().isEmpty() -> Toast.makeText(
                this,
                "Konten harus diisi terlebih dahulu",
                Toast.LENGTH_SHORT
            ).show()
            else -> {
                val title = binding.editTitle.text.toString()
                val text = binding.editFile.text.toString()
                val fileModel = FileModel().apply {
                    fileName = title
                    data = text
                }
                FileHelper.writeFile(fileModel, this)
                Toast.makeText(this, "Saving ${fileModel.fileName} file", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun showList() {
        /**
         * Returns an array of strings naming the private files
         * associated with this Context's application package.
         * return list of filename in private files
         * */
        val items = fileList()
        val builder = AlertDialog.Builder(this).apply {
            setTitle("Pilih file yang diinginkan")
            setItems(items) { _, item ->
                // onclick listener
                loadData(items[item].toString())
            }
        }
        val alert = builder.create()
        alert.show()
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this, title)
        with(binding) {
            editTitle.setText(fileModel.fileName)
            editFile.setText(fileModel.data)
            Toast.makeText(
                this@MainActivity,
                "Loading ${fileModel.fileName} data",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}