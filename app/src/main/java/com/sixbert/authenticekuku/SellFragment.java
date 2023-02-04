package com.sixbert.authenticekuku;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class SellFragment extends Fragment {

    private static final String TAG = "no error";
    private EditText phoneNumber;
    private EditText numberOfProduct;
    private EditText priceOfProduct;
    //private TextView productAutoTV;
    private Button add, edit, delete;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid;

    {
        assert currentUser != null;
        uid = currentUser.getPhoneNumber();
    }

    private ArrayAdapter<String> adapter;

    String[] product = {"Kuku Kienyeji", "Kuku Kisasa", "Mayai Kisasa (Trei)", "Mayai Kienyeji (Trei)",
            "Kuku Chotara", "Mayai Chotara (Trei)"};

    public SellFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sell, container, false);

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, product);

        AutoCompleteTextView autCompleteTV = rootView.findViewById(R.id.productTextView);

        autCompleteTV.setAdapter(adapter);
        autCompleteTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), autCompleteTV.getText() + " selected", Toast.LENGTH_SHORT).show();

            }
        });

        //phoneNumber = rootView.findViewById(R.id.edtxtphone);
        numberOfProduct = rootView.findViewById(R.id.edtxtnumber_of_chicken);
        priceOfProduct = rootView.findViewById(R.id.edtxtPrice);
        //productAutoTV =findViewById(R.id.productTextView);
        add = rootView.findViewById(R.id.btnAdd);
        edit = rootView.findViewById(R.id.btnEdit);
        delete= rootView.findViewById(R.id.btnDelete);

        //logout = rootView.findViewById(R.id.btnLogout);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_autocompleteTV = autCompleteTV.getText() + "";
                String txt_numberOfChicken = numberOfProduct.getText() + "";
                String txt_priceOfChicken = priceOfProduct.getText() + "";
                if (TextUtils.isEmpty(txt_numberOfChicken) && TextUtils.isEmpty(txt_priceOfChicken)) {
                    Toast.makeText(getActivity(), "Fields cannot be blank", Toast.LENGTH_SHORT).show();
                } else {

                    //addDataToFirestore(txt_autocompleteTV, txt_numberOfChicken, txt_priceOfChicken);

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("phoneNumber", uid);
                    map.put("typeOfItem", autCompleteTV.getText().toString());
                    //map.put("phone number", phoneNumber.getText().toString());
                    map.put("numberOfProduct", numberOfProduct.getText().toString());
                    map.put("priceOfProduct", priceOfProduct.getText().toString());
                    //FirebaseDatabase.getInstance().getReference().child("eKuku").child(autCompleteTV.getText()+"").updateChildren(map);

                    db.collection("eKuku")
                            .add(map)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    numberOfProduct.setText("");
                                    priceOfProduct.setText("");
                                    Toast.makeText(getActivity(), "Bidhaa zako zimeongezwa kikamilifu", Toast.LENGTH_SHORT).show();


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Bidhaa hazijaongezwa", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }

        });

        edit.setEnabled(false);
        ///


        delete.setEnabled(false);


        return rootView;




       /* ArrayAdapter<String> adapter = new ArrayAdapter<>(SellFragment.this, android.R.layout.simple_spinner_item, product);

        AutoCompleteTextView autCompleteTV = findViewById(R.id.productTextView);

        autCompleteTV.setAdapter(adapter);*/

        //Use OnItemClickLister instead of onItemSelectedListener
        //onItemSelectedListener works if you use toggle arrows or trackball

       /* autCompleteTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(View v){

            }
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), autCompleteTV.getText() +" selected", Toast.LENGTH_SHORT).show();

            }
        });*/

    }
}
/*
    private void addDataToFirestore(String qytOfProduct, String prOfProduct){
        CollectionReference dbSell = db.collection("eKuku");
        Sell sell = new Sell(qytOfProduct, prOfProduct);
        dbSell.add(sell).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getActivity(),"Bidhaa zako zimeongezwa kwenye kanzi data", Toast.LENGTH_SHORT ).show();

            }
        }).addOnFailureListener(new OnFailureListener(){
            public void onFailure(@NonNull Exception e){
                Toast.makeText(getActivity(), "Bidhaa hazijaongezwa \n" + e, Toast.LENGTH_SHORT).show();
            }
        });

    }


}

/*db.collection("users")
        .add(user)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
@Override
public void onSuccess(DocumentReference documentReference) {
        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
        }
        })
        .addOnFailureListener(new OnFailureListener() {
@Override
public void onFailure(@NonNull Exception e) {
        Log.w(TAG, "Error adding document", e);
        }
        });*/