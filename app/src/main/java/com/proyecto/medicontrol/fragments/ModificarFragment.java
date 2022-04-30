package com.proyecto.medicontrol.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.proyecto.medicontrol.R;
import com.proyecto.medicontrol.clases.conexionBD;
import com.proyecto.medicontrol.clases.utilidades;
import com.proyecto.medicontrol.interfaces.ComunicaFragments;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModificarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    View vista;
    Activity actividad;
    ComunicaFragments comunicacion;
    Button btnActualizar, btnEliminar;
    ImageButton btnAtras, btnBuscar;
    EditText codigoText, descripcionText, presentacionText, dosisText;

    public ModificarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EgresosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModificarFragment newInstance(String param1, String param2) {
        ModificarFragment fragment = new ModificarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_modificar, container, false);

        btnAtras = vista.findViewById(R.id.btnAtras);
        btnBuscar = vista.findViewById(R.id.btnBuscar);
        btnActualizar = vista.findViewById(R.id.btnActualizar);
        btnEliminar = vista.findViewById(R.id.btnEliminar);

        codigoText = vista.findViewById(R.id.codigoProducto);
        descripcionText = vista.findViewById(R.id.descripcionProducto);
        presentacionText = vista.findViewById(R.id.presentacionProducto);
        dosisText = vista.findViewById(R.id.dosisProducto);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codigoText.setText("");
                limpiarCampos();
                comunicacion.llamarMenu();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarProducto();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarProducto();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarProducto().show();
                limpiarCampos();
            }
        });

        return vista;
    }

    private void  buscarProducto(){
        conexionBD conn = new conexionBD(actividad, utilidades.nombreBD, null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        if (codigoText.getText().toString()!=null && !codigoText.getText().toString().trim().equals("")){
            try {
                Cursor cursor = db.rawQuery("SELECT descripcion, presentacion, dosis FROM " + utilidades.tablaProductos + " WHERE codigo = " + codigoText.getText().toString(), null);
                cursor.moveToFirst();
                descripcionText.setText(cursor.getString(0));
                presentacionText.setText(cursor.getString(1));
                dosisText.setText(cursor.getString(2));
                Toast.makeText(actividad.getApplicationContext(), "MOSTRANDO PRODUCTO", Toast.LENGTH_SHORT).show();
                cursor.close();
            } catch (Exception e) {
                Toast.makeText(actividad.getApplicationContext(), "PRODUCTO NO ENCONTRADO", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            }
        }else{
            Toast.makeText(actividad,"INGRESE CODIGO A BUSCAR!!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private void actualizarProducto(){
        if (descripcionText.getText().toString()!=null && !descripcionText.getText().toString().trim().equals("") && presentacionText.getText().toString()!=null && !presentacionText.getText().toString().trim().equals("") && dosisText.getText().toString()!=null && !dosisText.getText().toString().trim().equals("")){
            conexionBD conn = new conexionBD(actividad, utilidades.nombreBD, null, 1);
            SQLiteDatabase db = conn.getWritableDatabase();

            String[] parametro = {codigoText.getText().toString()};

            ContentValues values = new ContentValues();
            values.put(utilidades.campoDescripcion, descripcionText.getText().toString());
            values.put(utilidades.campoPresentacion, presentacionText.getText().toString());
            values.put(utilidades.campoDosis, dosisText.getText().toString());

            db.update(utilidades.tablaProductos, values, utilidades.campoCodigo + "=?", parametro);
            db.close();
            Toast.makeText(actividad,"PRODUCTO ACTUALIZADO EXITOSAMENTE!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(actividad,"DATOS INCOMPLETOS!!", Toast.LENGTH_SHORT).show();
        }
    }

    public AlertDialog eliminarProducto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(actividad);

        builder.setTitle("ALERTA")
                .setMessage("Una vez eliminado el producto no se podra recuperar.")
                .setNegativeButton("ELIMINAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        conexionBD conn = new conexionBD(actividad, utilidades.nombreBD, null, 1);
                        SQLiteDatabase db = conn.getWritableDatabase();

                        String[] parametro = {codigoText.getText().toString()};

                        db.delete(utilidades.tablaProductos, utilidades.campoCodigo + "=?", parametro);
                        db.close();
                        Toast.makeText(actividad,"PRODUCTO ELIMINADO EXITOSAMENTE!!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

    private void limpiarCampos(){
        descripcionText.setText("");
        presentacionText.setText("");
        dosisText.setText("");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.actividad=(Activity) context;
            comunicacion = (ComunicaFragments) this.actividad;
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}