package com.example.atalante.sos;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Contact extends AppCompatActivity {
    private static final int REQ_PICK_CONTACT = 2;
    private TextView firstPhoneText;
    ImageView pickContact;
    Button contactSaveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        firstPhoneText = (TextView) findViewById(R.id.firstPhoneText);
        contactSaveButton = (Button) findViewById(R.id.contactSaveButton);
        pickContact = (ImageView) findViewById(R.id.pickContact);


        pickContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 2);

            }
        });

        /*pickContact2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
                intent2.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent2, 2);

            }
        }); */

        addListenerOnButton();
    }

    public void addListenerOnButton() {

        contactSaveButton = (Button) findViewById(R.id.contactSaveButton);
        contactSaveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                firstPhoneText = (TextView) findViewById(R.id.firstPhoneText);
                String number1 = firstPhoneText.getText().toString();


                Intent myIntent = new Intent(view.getContext(),HelpScreen.class);
                myIntent.putExtra("Phone1",number1);
                startActivity(myIntent);

              /*  secondPhoneText = (TextView) findViewById(R.id.secondPhoneText);
                String number2 = secondPhoneText.getText().toString();

                Intent myIntent2 = new Intent(view.getContext(),HelpScreen.class);
                myIntent2.putExtra("Phone2",number2);
                startActivity(myIntent2);
                */


            }

        });
    }

    String number1;
    String number2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQ_PICK_CONTACT) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                Cursor cursor = managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();

                number1 = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                firstPhoneText.setText(number1);
            }
        }



    }

}
