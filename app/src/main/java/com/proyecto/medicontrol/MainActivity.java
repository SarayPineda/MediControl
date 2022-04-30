package com.proyecto.medicontrol;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.proyecto.medicontrol.fragments.EgresosFragment;
import com.proyecto.medicontrol.fragments.IngresosFragment;
import com.proyecto.medicontrol.fragments.InicioFragment;
import com.proyecto.medicontrol.fragments.ModificarFragment;
import com.proyecto.medicontrol.fragments.RegistroFragment;
import com.proyecto.medicontrol.interfaces.ComunicaFragments;

public class MainActivity extends AppCompatActivity implements ComunicaFragments, InicioFragment.OnFragmentInteractionListener, RegistroFragment.OnFragmentInteractionListener, ModificarFragment.OnFragmentInteractionListener, IngresosFragment.OnFragmentInteractionListener, EgresosFragment.OnFragmentInteractionListener{

    Fragment fragmentoInicio, fragmentoRegistro, fragmentoModificar, fragmentoIngresos, fragmentoEgresos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideSystemUI();

        fragmentoInicio = new InicioFragment();
        fragmentoRegistro = new RegistroFragment();
        fragmentoModificar = new ModificarFragment();
        fragmentoIngresos = new IngresosFragment();
        fragmentoEgresos  = new EgresosFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments, fragmentoInicio).commit();
    }

    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("GESTIONAR PRODUCTOS")
                .setMessage("Indique si desea registrar un producto nuevo o si desea modificar uno ya existente.\n\n" +
                        "También podrá eliminar un producto desde la opción modificar")
                .setNegativeButton("REGISTRAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments, fragmentoRegistro).commit();
                            }
                        })
                .setPositiveButton("MODIFICAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments, fragmentoModificar).commit();
                            }
                        });
        return builder.create();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void llamarMenu() {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments, fragmentoInicio).commit();
    }

    @Override
    public void llamarProductos() {
        createSimpleDialog().show();
    }

    @Override
    public void llamarInventario() {

    }

    @Override
    public void llamarIngresos() {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments, fragmentoIngresos).commit();
    }

    @Override
    public void llamarEgresos() {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments, fragmentoEgresos).commit();
    }

    @Override
    public void llamarAjustes() {

    }

    private void hideSystemUI() {
        View decorView = this.getWindow().getDecorView();
        int uiOptions = decorView.getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        newUiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE;
        newUiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(newUiOptions);
    }

    private void showSystemUI() {
        View decorView = this.getWindow().getDecorView();
        int uiOptions = decorView.getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions &= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
        newUiOptions &= ~View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions &= ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions &= ~View.SYSTEM_UI_FLAG_IMMERSIVE;
        newUiOptions &= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(newUiOptions);
    }
}