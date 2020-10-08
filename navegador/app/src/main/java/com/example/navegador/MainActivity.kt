package com.example.navegador

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.CheckedTextView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.settings_dialog.view.*


class MainActivity : AppCompatActivity() {

    var mainLayout: View? = null
    var toolbar: Toolbar? = null
    var webView: WebView? = null

    val SHAREDPREF_FILENAME = "com.example.navegador.web_prefs"
    var sharedPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainLayout = layoutInflater.inflate(R.layout.activity_main, null)
        setContentView(mainLayout)

        title = ""

        sharedPref = this.getSharedPreferences(SHAREDPREF_FILENAME,0)

        toolbar = this.mainLayout?.toolbar
        setSupportActionBar(toolbar)

        var LoadLastViewed : Boolean = false
        LoadLastViewed = sharedPref!!.getBoolean("LoadLastURL", false)

        if(LoadLastViewed){
            sharedPref!!.getString("LastVisitedURL", "https://google.com")?.let { setUrl(it) }
        }else{
            sharedPref!!.getString("HomePageURL", "https://google.com")?.let { setUrl(it) }
        }

    }

    @SuppressLint("SetJavaScriptEnable")
    private fun setUrl(url: String) {
        var webUrl = url

        //conferir se e true ou false e resgatar
        if(!webUrl.startsWith("http")){
            webUrl = "http://$url"
        }

        webView = findViewById<WebView>(R.id.webBrowser)
        webView!!.getSettings().javaScriptEnabled = true
        webView!!.getSettings().javaScriptCanOpenWindowsAutomatically = true
        webView!!.webViewClient = object : WebViewClient(){}

        webView!!.loadUrl(webUrl)
        mainLayout?.webUrl!!.setText(webUrl)

        //para salvar
        sharedPref?.edit()?.putString("LastVisitedURL", webUrl)?.apply()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //receber o item que foi clicado
        val id = item.getItemId()

        when (id){
            R.id.actionGo -> navigation()
            R.id.actionHome -> home()
            R.id.Refresh -> reload()
            R.id.actionSettings -> config()
            else -> showToast(getString(R.string.no_click))
        }

        return super.onOptionsItemSelected(item)
    }

    private fun home() {
        //carregar as preferes
        sharedPref!!.getString("HomePageURL", "https://google.com")?.let { setUrl(it) }
    }

    private fun reload() {

        val webUrlString = webView!!.url
        //verificar
        if(webUrlString.isNullOrEmpty()){
            if(!mainLayout?.webUrl!!.getText().trim().isNullOrEmpty()){
                setUrl(mainLayout?.webUrl!!.text.toString())
            }
        }else{
            setUrl(webUrlString)
        }
    }

    private fun navigation() {

        //verificar se tem alguma coisa escrita na barra de endereço
        if(mainLayout?.webUrl!!.text.trim().isNullOrEmpty()){
            showToast(getString(R.string.no_url))
        }else{
            setUrl(mainLayout?.webUrl!!.text.toString())
        }

    }

    private fun config() {

        val alertDialogBuilder = AlertDialog.Builder(this)

        val dialogLayout = layoutInflater.inflate(R.layout.settings_dialog, null)
        alertDialogBuilder.setView(dialogLayout)

        val homePageEditTExt = dialogLayout.txtHomePageUrl as EditText
        homePageEditTExt.setText(sharedPref!!.getString("HomePageURL","https://google.com"))

        val checkedTextView = dialogLayout.checkedTextView as CheckedTextView
        //mostra ve se está selecionado ou não
        var showCheck = sharedPref!!.getBoolean("LoadLastURL", false)
        checkedTextView.setCheckMarkDrawable(
            if(showCheck) R.drawable.checked
            else R.drawable.unchecked )

        checkedTextView.setOnClickListener {
            checkedTextView.isChecked = !checkedTextView.isChecked()
            checkedTextView.setCheckMarkDrawable(
                if(checkedTextView.isChecked()) R.drawable.checked
                else R.drawable.unchecked )
            //botão se está seleciona ou não
            sharedPref?.edit()?.putBoolean(("LoadLastURL"),checkedTextView.isChecked())?.apply()
        }

        // botão de cancelar e de salvar
        alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton(getString(R.string.btn_save)
            ){dialog, id ->
                sharedPref?.edit()?.putString("HomePageURL", homePageEditTExt.text.toString())?.apply()
            }
            .setNegativeButton(getString(R.string.btn_cancel)
            ){dialog, id -> dialog.cancel()}
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this.applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    //função para quando aperta botão voltar para pagina
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if(event.getAction() === KeyEvent.ACTION_DOWN){
            when(keyCode){
                KeyEvent.KEYCODE_BACK->{
                    if(webView!!.canGoBack()){
                        webView!!.goBack()
                    }else{
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }



}