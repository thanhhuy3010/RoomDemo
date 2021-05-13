//package com.example.demoroomdb.ViewModel;
//
//import android.app.Application;
//
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//
//import com.example.demoroomdb.model.Entity.Tablet;
//import com.example.demoroomdb.TabletRepository;
//
//import java.util.List;
//
//public class TabletViewModel extends AndroidViewModel {
//    private TabletRepository tabletRepository;
//
//    private LiveData<List<Tablet>> mAlltablet;
//
//    /**
//     * create constructor
//     * @param application
//     */
//    public TabletViewModel( Application application) {
//        super(application);
//        tabletRepository = new TabletRepository(application);
//        mAlltablet = tabletRepository.getGetAllTablet();
//    }
//
//    LiveData<List<Tablet>> getAlltablet() {
//        return mAlltablet;
//    }
//
//    public void insert(Tablet word) { tabletRepository.insert(word); }
//
//
//}
