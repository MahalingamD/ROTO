package com.ti.rotogro.ui.home;


import com.ti.rotogro.data.db.entity.Product;

public interface HomeRecyclerItemClickListener {
    void onItemClick( int position, Product notice );
}