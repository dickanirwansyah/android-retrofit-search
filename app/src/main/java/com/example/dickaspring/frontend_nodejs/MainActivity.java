package com.example.dickaspring.frontend_nodejs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.dickaspring.frontend_nodejs.adapter.PersonAdapter;
import com.example.dickaspring.frontend_nodejs.consume_api.ISearchApi;
import com.example.dickaspring.frontend_nodejs.consume_api.RetrofitClient;
import com.example.dickaspring.frontend_nodejs.model.Person;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    ISearchApi myApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    RecyclerView recycler_search;
    LinearLayoutManager layoutManager;
    //person adapter
    PersonAdapter personAdapter;

    //material search bar
    MaterialSearchBar materialSearchBar;

    List<String> suggestList = new ArrayList<>();

    //Ctrl+o
    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init api
        myApi = getApi();

        //view
        recycler_search = (RecyclerView) findViewById(R.id.recyler_search);
        recycler_search.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_search.setLayoutManager(layoutManager);
        recycler_search.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

        materialSearchBar = (MaterialSearchBar) findViewById(R.id.search_bar);
        materialSearchBar.setCardViewElevation(10);

        //add suggestlist
        addSuggestList();

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for (String search_term : suggestList)
                    if (search_term.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search_term);
                materialSearchBar.setLastSuggestions(suggest);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)
                    getAllPerson();
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        getAllPerson();
    }

    private ISearchApi getApi() {
        return RetrofitClient.getInstance()
                .create(ISearchApi.class);
    }

    private void startSearch(String query){
        compositeDisposable.add(myApi.searchPerson(query)
            .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Person>>() {
            @Override
            public void accept(List<Person> personList) throws Exception {
                personAdapter = new PersonAdapter(personList);
                recycler_search.setAdapter(personAdapter);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(MainActivity.this, "Notfound data", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void getAllPerson(){
        compositeDisposable.add(myApi.getPersonList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Person>>() {
            @Override
            public void accept(List<Person> personList) throws Exception {
                personAdapter = new PersonAdapter(personList);
                recycler_search.setAdapter(personAdapter);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(MainActivity.this, "Notfound data", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void addSuggestList(){
        suggestList.add("dicka");
        suggestList.add("dicky");
        suggestList.add("dafi");
        suggestList.add("david");
        suggestList.add("dion");
        suggestList.add("doni");

        materialSearchBar.setLastSuggestions(suggestList);
    }
}
