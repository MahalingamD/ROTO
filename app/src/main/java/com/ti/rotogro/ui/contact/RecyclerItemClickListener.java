package com.ti.rotogro.ui.contact;


import com.ti.rotogro.data.db.entity.AddressMaster;
import com.ti.rotogro.model.ContactDetail;

public interface RecyclerItemClickListener {
   void onItemClick( AddressMaster notice );
}