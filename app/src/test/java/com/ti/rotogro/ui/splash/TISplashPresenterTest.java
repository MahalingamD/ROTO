package com.ti.rotogro.ui.splash;

import com.ti.rotogro.model.Data;
import com.ti.rotogro.service.RetrofitInstance;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertTrue;

public class TISplashPresenterTest {


    private RetrofitInstance myRetrofitInstance;

    public TISplashPresenterTest(){
        if( this.myRetrofitInstance == null ) {
            myRetrofitInstance = new RetrofitInstance();
        }
    }

    @Test
    public void initPresenter() {
    }

    @Test
    public void checkAppUpdate() {

       // TIRotogroAPI mockedApiInterface = Mockito.mock(TIRotogroAPI.class);
       // Call<Data> mockedCall = Mockito.mock(Call.class);

        Call<Data> call=  myRetrofitInstance.getAPI().getVersionResult("GetAppUpdate", "2.0");


        try {
            Response<Data> response=call.execute();
            Data authResponse = response.body();
            System.out.println("Hello StackOverflow");
           if( authResponse.getResponse().getResponse_code().equals("1")){
               System.out.println("test pass");
           }else{
               System.out.println("test fail");
           }
            assertTrue("Test pass",response.isSuccessful());


        } catch (IOException e) {
            System.out.println("test fail");
            e.printStackTrace();
        }

    }
}