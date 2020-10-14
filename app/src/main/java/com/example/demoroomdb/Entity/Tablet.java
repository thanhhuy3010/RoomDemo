package com.example.demoroomdb.Entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tablet_entity")
public class Tablet {

    @NonNull
    @PrimaryKey
    public String tablet_id;

    @ColumnInfo(name = "mode")
    public String mode;

    @ColumnInfo(name = "connection_status")
    public String connection_status;

    @ColumnInfo(name = "port")
    public int port;

    @ColumnInfo(name = "ip_address")
    public String ip_address;

    public Tablet(@NonNull String tablet_id) {this.tablet_id = tablet_id;}

    public String getTablet(){return this.tablet_id;}
}
