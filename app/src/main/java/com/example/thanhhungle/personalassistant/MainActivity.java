package com.example.thanhhungle.personalassistant;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    public static final String LINE_SEPARATOR = "\n";
    private static final String TAG = MainActivity.class.getSimpleName();

    TextView mTextView;

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // test
//                testDisposable();
                testFlowable();
            }
        });

        // bind item
        mTextView = (TextView) findViewById(R.id.tvText);

    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    void testDisposable() {
        mCompositeDisposable.add(sampleObservable()
                // run on background thread
                .subscribeOn(Schedulers.io())
                // be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onComplete() {
                        mTextView.append(" onComplete");
                        mTextView.append(LINE_SEPARATOR);
                        Log.d(TAG, " onComplete");
                    }

                    @Override
                    public void onNext(String value) {
                        mTextView.append(" onNext : value : " + value);
                        mTextView.append(LINE_SEPARATOR);
                        Log.d(TAG, " onNext value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mTextView.append(" onError : " + e.getMessage());
                        mTextView.append(LINE_SEPARATOR);
                        Log.d(TAG, " onError : " + e.getMessage());

                    }
                })
        );
    }

    void testFlowable() {
        Flowable<Integer> flowable = Flowable.just(1, 2, 3, 4);
        flowable.reduce(10, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(getSubscriber());
    }

    @android.support.annotation.NonNull
    private SingleObserver<Integer> getSubscriber() {
        return new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onSuccess(Integer integer) {
                mTextView.append(" on Success : integer = " + integer);
                mTextView.append(LINE_SEPARATOR);
                Log.d(TAG, " onSuccess : integer : " + integer);
            }

            @Override
            public void onError(Throwable e) {
                mTextView.append(" on Error : " + e.getMessage());
                mTextView.append(LINE_SEPARATOR);
                Log.d(TAG, " onError : " + e.getMessage());
            }
        };
    }


    static Observable<String> sampleObservable() {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {

            @Override
            public ObservableSource<? extends String> call() throws Exception {
                // Do some long running operation
                SystemClock.sleep(2000);
                return Observable.just("one", "two", "three", "four", "five");
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
