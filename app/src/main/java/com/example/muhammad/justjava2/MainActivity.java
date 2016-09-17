package com.example.muhammad.justjava2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public int price = 2;
    public int qunatity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //increment the quantity
    public void increment(View view) {
        if (this.qunatity <=100) {
            this.qunatity = this.qunatity + 1;
            display(this.qunatity);
        }else{
            Toast.makeText(this, "You can not have more than 100 coffees", Toast.LENGTH_SHORT).show();
        }
    }

    //decrement quantity
    public void decrement(View view) {
        if (this.qunatity > 1) {
            this.qunatity = this.qunatity - 1;
            display(this.qunatity);

        } else {
            Toast.makeText(this, "You can not have less than 1 coffee", Toast.LENGTH_SHORT).show();
        }

    }

    //when order place it will show total amount
    public void submitOrder(View view) {



        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        EditText userName = (EditText) findViewById(R.id.name);
        String name = userName.getText().toString();
        //Log.v("MainActivity","Name "+name);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int totalPrice = calculatePrice(hasWhippedCream,hasChocolate);

        String order_summary_message = createOrderSummary(totalPrice,hasWhippedCream,hasChocolate,name);


        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:47.6, -122.63"));
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
*/
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava Order for CoffeeShop");
        intent.putExtra(Intent.EXTRA_TEXT, order_summary_message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }
        //this.qunatity = 0;

    }


    private String createOrderSummary(int totalPrice, boolean addWhippedCream, boolean addChocolate, String name){

        String orderSummary = getString(R.string.order_summary_name,name)+
                "\n"+getString(R.string.order_summary_whipped_cream,addWhippedCream)+
                "\n"+getString(R.string.order_summary_chocolate,addChocolate)+
                "\n"+getString(R.string.order_summary_quantity,qunatity)+
                "\n"+getString(R.string.order_summary_price,totalPrice) +
                "\n"+getString(R.string.thank_you);
        return orderSummary;
    }

    private int calculatePrice(boolean addCream, boolean addChocolate){
        int basePrice = price;
        //cream topping 1$
        if(addCream){
            basePrice = basePrice + 1;
        }
        //chocolate topping 2$
        if(addChocolate){
            basePrice = basePrice + 2;
        }
        return basePrice * qunatity ;
    }

    //show quantity
    private void display(int number) {
        TextView quantity_view = (TextView) findViewById(R.id.quantity_text_view);
        quantity_view.setText("" + number);
    }

}
