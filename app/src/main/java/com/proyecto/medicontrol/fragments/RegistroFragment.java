package com.proyecto.medicontrol.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

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
 * Use the {@link RegistroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroFragment extends Fragment {

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
    Button btnGuardar;
    ImageButton btnAtras;
    EditText descripcionText, presentacionText, dosisText;

    public RegistroFragment() {
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
    public static RegistroFragment newInstance(String param1, String param2) {
        RegistroFragment fragment = new RegistroFragment();
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
        vista = inflater.inflate(R.layout.fragment_registro, container, false);

        btnGuardar = vista.findViewById(R.id.btnActualizar);
        btnAtras = vista.findViewById(R.id.btnAtras);

        descripcionText = vista.findViewById(R.id.descripcionProducto);
        presentacionText = vista.findViewById(R.id.presentacionProducto);
        dosisText = vista.findViewById(R.id.dosisProducto);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarCampos();
                comunicacion.llamarMenu();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarProducto();
            }
        });

        return vista;
    }

    private void registrarProducto(){
        if (descripcionText.getText().toString()!=null && !descripcionText.getText().toString().trim().equals("") && presentacionText.getText().toString()!=null && !presentacionText.getText().toString().trim().equals("") && dosisText.getText().toString()!=null && !dosisText.getText().toString().trim().equals("")){
            conexionBD conn = new conexionBD(actividad, utilidades.nombreBD, null, 1);
            SQLiteDatabase db = conn.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(utilidades.campoDescripcion, descripcionText.getText().toString());
            values.put(utilidades.campoPresentacion, presentacionText.getText().toString());
            values.put(utilidades.campoDosis, dosisText.getText().toString());

            Long codigoResultante = db.insert(utilidades.tablaProductos, utilidades.campoCodigo, values);

            if(codigoResultante!=-1){
                Toast.makeText(actividad,"REGISTRO NÚMERO " + codigoResultante + " EXITOSO!!", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            }else
                Toast.makeText(actividad,"REGISTRO FALLIDO!!", Toast.LENGTH_SHORT).show();
            db.close();
        }else{
            Toast.makeText(actividad,"DATOS INCOMPLETOS!!", Toast.LENGTH_SHORT).show();
        }
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