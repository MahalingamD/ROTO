package com.ti.rotogro.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ti.rotogro.BuildConfig;
import com.ti.rotogro.R;
import com.ti.rotogro.base.APPFragmentManager;
import com.ti.rotogro.base.BaseActivity;
import com.ti.rotogro.data.db.AppDatabase;
import com.ti.rotogro.data.db.entity.LanguageMaster;
import com.ti.rotogro.helper.TIHelper;
import com.ti.rotogro.preference.TIPrefs;
import com.ti.rotogro.ui.dialog.RecyclerItemClickListener;
import com.ti.rotogro.ui.dialog.adapter.TILanguageRecyclerAdapter;
import com.ti.rotogro.ui.home.TIHomeFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TIMainActivity extends BaseActivity implements TIMainContract.View {

   public static String TAG = TIMainActivity.class.getSimpleName();

   private AppCompatActivity myContext;
   private Toolbar myToolbar;
   private DrawerLayout mDrawer;
   private NavigationView mNavigationView;
   private TextView mAppVersionTextView;

   private TextView mNameTextView;

   private TextView myMobileTextView;

   private ActionBarDrawerToggle mDrawerToggle;

   TIMainPresenter myTiMainPresenter;
   private ImageView mProfileImageView;
   ImageView myRightMenu;
   APPFragmentManager myFragmentManager;

   private RecyclerView myRecyclerView;
   private TILanguageRecyclerAdapter myAdapter;
   List<LanguageMaster> myLanguageMasterList;


   LanguageMaster mySelectLanguageMaster;
   AppDatabase myAppDatabase;


   //For oreo and updated version we need this for locale change
   @Override
   protected void attachBaseContext( Context newBase ) {
      if( Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1 ) {
         String aString = TIPrefs.getString( "sel_language", "" );
         newBase = TIHelper.changeLang( newBase, aString );
      }
      super.attachBaseContext( newBase );
   }

   @Override
   protected void onCreate( Bundle savedInstanceState ) {
      super.onCreate( savedInstanceState );

      //  LanguageMaster aLanguageMaster = TIPrefs.getObject( "language", LanguageMaster.class );
      // String aLang = firstTwoChar( aLanguageMaster.getLan_Name() );
      String aString = TIPrefs.getString( "sel_language", "" );
      setLocale( aString );

      setContentView( R.layout.activity_main );

      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
      StrictMode.setThreadPolicy( policy );

      myContext = this;


      myTiMainPresenter = new TIMainPresenter( this, myContext );
      myTiMainPresenter.attachView( this );
      myTiMainPresenter.initPresenter();
   }


   @Override
   public void initActivity() {

      myAppDatabase = AppDatabase.getDatabase( myContext );
      myFragmentManager = new APPFragmentManager( myContext );

      myToolbar = findViewById( R.id.toolbar );
      mDrawer = findViewById( R.id.drawer_view );
      mNavigationView = findViewById( R.id.navigation_view );
      mAppVersionTextView = findViewById( R.id.tv_app_version );
      myRightMenu = findViewById( R.id.activity_menu_right );

      setUp();
   }

   private void setUp() {
      setSupportActionBar( myToolbar );
      mDrawerToggle = new ActionBarDrawerToggle( this, mDrawer, myToolbar,
              R.string.open_drawer,
              R.string.close_drawer ) {
         @Override
         public void onDrawerOpened( View drawerView ) {
            super.onDrawerOpened( drawerView );
            hideKeyboard();
         }

         @Override
         public void onDrawerClosed( View drawerView ) {
            super.onDrawerClosed( drawerView );
         }
      };
      mDrawer.addDrawerListener( mDrawerToggle );
      mDrawerToggle.syncState();
      setupNavMenu();
      myTiMainPresenter.onNavMenuCreated();

      myToolbar.setNavigationIcon( null );          // to hide Navigation icon
      mDrawerToggle.setDrawerIndicatorEnabled( false );


      myRightMenu.setOnClickListener( v -> {
         if( mDrawer.isDrawerOpen( GravityCompat.END ) ) {
            mDrawer.closeDrawer( GravityCompat.END );
         } else {
            mDrawer.openDrawer( GravityCompat.END );
         }
      } );

   }


   void setupNavMenu() {
      View headerLayout = mNavigationView.getHeaderView( 0 );
      mProfileImageView = headerLayout.findViewById( R.id.iv_profile_pic );
      mNameTextView = headerLayout.findViewById( R.id.header_name );
      myMobileTextView = headerLayout.findViewById( R.id.header_mobile_fragment );

      mNavigationView.setNavigationItemSelectedListener(
              item -> {
                 mDrawer.closeDrawer( GravityCompat.END );
                 switch( item.getItemId() ) {
                    case R.id.nav_item_home:
                       myTiMainPresenter.onHomeMenuClick();
                       return true;
                    case R.id.nav_item_language: {
                       myTiMainPresenter.onLanguageClick();
                    }
                    return true;
                    case R.id.nav_item_about:
                       myTiMainPresenter.onAboutClick();
                       return true;
                    default:
                       return false;
                 }
              } );
   }

   @Override
   public void defaultScreen() {
      myFragmentManager.updateContent( new TIHomeFragment(), TIHomeFragment.TAG, null );
   }

   @Override
   public void openHomeFragment() {
      myFragmentManager.clearAllFragments();
      myFragmentManager.updateContent( new TIHomeFragment(), TIHomeFragment.TAG, null );
   }


   @Override
   public void onBackPressed() {
      int aCount = myFragmentManager.getBackstackCount();
      if( aCount == 1 ) {
         if( getCurrentFragment() instanceof TIHomeFragment )
            ( ( TIHomeFragment ) getCurrentFragment() ).onBackPressed();
      } else
         myFragmentManager.onBackPress();

   }

   @Override
   public void showAboutFragment() {

   }

   @Override
   public void showLanguageFragment() {
      showAlertDialog();
   }

   private void showAlertDialog() {
      AlertDialog.Builder builder = new AlertDialog.Builder( myContext );
      builder.setTitle( R.string.label_select_language );

      myLanguageMasterList = new ArrayList<>();

      LayoutInflater inflater = getLayoutInflater();
      View dialogView = inflater.inflate( R.layout.fragment_my_dialog, null );

      myRecyclerView = dialogView.findViewById( R.id.myLanguageRecyclerView );

      RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( myContext, LinearLayoutManager.VERTICAL, false );
      myRecyclerView.setLayoutManager( layoutManager );

      myAdapter = new TILanguageRecyclerAdapter( myContext, myLanguageMasterList, recyclerItemClickListener );
      myRecyclerView.setAdapter( myAdapter );

      setValues();

      builder.setView( dialogView );

      builder.setPositiveButton( R.string.label_ok, ( dialogInterface, i ) -> {
         if( mySelectLanguageMaster != null ) {
            TIPrefs.putObject( "language", mySelectLanguageMaster );

            String aLang;
            switch( mySelectLanguageMaster.getLan_id() ) {
               case "1":
                  //aLang = firstTwoChar( myLanguageMaster.getLan_Name() );
                  aLang = "ta";
                  setLocale( aLang );
                  break;
               case "2":
                  aLang = "en";
                  // aLang = firstTwoChar( myLanguageMaster.getLan_Name() );
                  setLocale( aLang );
                  break;
               case "3":
                  // aLang = firstTwoChar( myLanguageMaster.getLan_Name() );
                  aLang = "te";
                  setLocale( aLang );
                  break;
               case "4":
                  // aLang = firstTwoChar( myLanguageMaster.getLan_Name() );
                  aLang = "hi";
                  setLocale( aLang );
                  break;
               default:
                  setLocale( "en" );
                  break;
            }

            myFragmentManager.clearAllFragments();
            refresh();
         }

         dialogInterface.dismiss();
      } );

      final AlertDialog dialog = builder.create();

      dialog.show();
   }

   private void refresh() {
      Intent intent = getIntent();
      overridePendingTransition( 0, 0 );
      intent.addFlags( Intent.FLAG_ACTIVITY_NO_ANIMATION );
      finish();
      overridePendingTransition( 0, 0 );
      startActivity( intent );
   }


   public String firstTwoChar( String str ) {
      return str.length() < 2 ? str : str.substring( 0, 2 ).toLowerCase();
   }

   private void setValues() {
      List<LanguageMaster> aLanguageMasterList = myAppDatabase.languageModel().getAllLanguages( 1 );

      myAdapter.updateAdapter( aLanguageMasterList );
   }

   @Override
   public void updateUserName( String currentUserName ) {

      mNameTextView.setText( myContext.getResources().getString( R.string.label_welcome ) );
   }

   @Override
   public void updateUserMobile( String currentUserMobile ) {
      myMobileTextView.setText( currentUserMobile );
   }

   @Override
   public void updateUserProfilePic( String currentUserProfilePicUrl ) {
      mProfileImageView.setImageDrawable( getDrawable( R.drawable.logo ) );
   }

   @Override
   public void updateAppVersion() {
      mAppVersionTextView.setText( new StringBuilder().append( myContext.getResources().getString( R.string.label_version ) ).append( " " ).append( BuildConfig.VERSION_NAME ).toString() );
   }


   @Override
   public void closeNavigationDrawer() {
      if( mDrawer != null ) {
         mDrawer.closeDrawer( Gravity.END );
      }
   }

   @Override
   public void lockDrawer() {

   }

   @Override
   public void unlockDrawer() {

   }

   private Fragment getCurrentFragment() {
      FragmentManager aFragmentManager = getSupportFragmentManager();
      String aFragmentTag = aFragmentManager.getBackStackEntryAt( aFragmentManager.getBackStackEntryCount() - 1 ).getName();
      return getSupportFragmentManager().findFragmentByTag( aFragmentTag );
   }


   private RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener() {
      @Override
      public void onItemClick( LanguageMaster aLanguageMaster ) {
         mySelectLanguageMaster = aLanguageMaster;

      }

   };


   public void setLocale( String lang ) {
      try {
         TIPrefs.putString( "sel_language", lang );

         String country = "IN";
         Locale locale = new Locale( lang, country );

         Resources activityRes = getResources();
         Configuration activityConf = activityRes.getConfiguration();

         activityConf.setLocale( locale );
         activityRes.updateConfiguration( activityConf, activityRes.getDisplayMetrics() );

         Resources applicationRes = getApplicationContext().getResources();
         Configuration applicationConf = applicationRes.getConfiguration();
         applicationConf.setLocale( locale );
         applicationRes.updateConfiguration( applicationConf, applicationRes.getDisplayMetrics() );
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }
}
