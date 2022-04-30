package com.proyecto.medicontrol.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.medicontrol.R;
import com.proyecto.medicontrol.clases.conexionBD;
import com.proyecto.medicontrol.clases.utilidades;
import com.proyecto.medicontrol.interfaces.ComunicaFragments;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EgresosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EgresosFragment extends Fragment {

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
    ImageButton btnAtras, btnBuscar, btnCalendario;
    EditText codigoText, descripcionText, cantidadText;
    TextView presentacionText, dosisText, fechaText;

    public EgresosFragment() {
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
    public static EgresosFragment newInstance(String param1, String param2) {
        EgresosFragment fragment = new EgresosFragment();
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
        vista = inflater.inflate(R.layout.fragment_egresos, container, false);

        btnAtras = vista.findViewById(R.id.btnAtras);
        btnBuscar = vista.findViewById(R.id.btnBuscar);
        btnCalendario = vista.findViewById(R.id.btnCalendario);

        codigoText = vista.findViewById(R.id.codigoProducto);
        descripcionText = vista.findViewById(R.id.descripcionProducto);
        presentacionText = vista.findViewById(R.id.presentacionProducto);
        dosisText = vista.findViewById(R.id.dosisProducto);
        cantidadText = vista.findViewById(R.id.cantidadEgreso);
        fechaText = vista.findViewById(R.id.fechaEgreso);

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
            public void onClick(View view) { buscarProducto(); }
        });

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturarFecha();
            }
        });

        return vista;
    }

    private void capturarFecha(){
        TextView text;
        int dia, mes, ano;

        text = vista.findViewById(R.id.fechaEgreso);

        final Calendar fecha = Calendar.getInstance();
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        mes = fecha.get(Calendar.MONTH);
        ano = fecha.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                text.setText(year + "/" + (month+1) + "/" + day);
            }
        }, dia, mes, ano);
        datePickerDialog.show();
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

    private void limpiarCampos(){
        descripcionText.setText("");
        presentacionText.setText(" Presentación");
        dosisText.setText(" Dosis");
        cantidadText.setText("");
        fechaText.setText("Fecha de egreso");
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