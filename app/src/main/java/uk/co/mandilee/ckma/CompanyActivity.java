package uk.co.mandilee.ckma;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class CompanyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.company_email)});
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
                try {
                    startActivity(Intent.createChooser(i, getString(R.string.send_mail)));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(CompanyActivity.this, R.string.no_email_clients, Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton facebook = (ImageButton) findViewById(R.id.facebook_button);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBrowser(getString(R.string.facebook_url));
            }
        });

        ImageButton website = (ImageButton) findViewById(R.id.website_button);
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBrowser(getString(R.string.website_url));
            }
        });

        /*
         * grab the collapsing layout so we can show/hide the title
         */
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        /*
         * grab the app bar layout so we can act when it changes state
         */
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);

        // set the title blank straight off!
        collapsingToolbarLayout.setTitle(" "); // single space intentional!


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.company_long));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" "); // single space intentional!
                    isShow = false;
                }
            }
        });
    }


    public void openBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.website_url)));
        try {
            startActivity(browserIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(CompanyActivity.this, R.string.no_browsers, Toast.LENGTH_SHORT).show();
        }
    }

}
