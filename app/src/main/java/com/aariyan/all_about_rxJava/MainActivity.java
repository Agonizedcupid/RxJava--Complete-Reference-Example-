package com.aariyan.all_about_rxJava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aariyan.all_about_rxJava.Model.StudentModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private List<StudentModel> listOfStudents = new ArrayList<>();
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getAllStudent();

        /**
         * I will be implementing most used RxJava operators:
         * Here is the SINGLE O
         */
        singleOperators();

    }

    private void singleOperators() {
        Single<Long> singleObservable = Single.create(new SingleOnSubscribe<Long>() {
                    @Override
                    public void subscribe(@NonNull SingleEmitter<Long> emitter) throws Throwable {
                        if (!emitter.isDisposed()) {
                            Log.d("SINGLE_OPERATOR", "onSuccess-00: "+Thread.currentThread().getName());
                            emitter.onSuccess(15L);
                        }
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        //Registering Through SingleObserver:
        SingleObserver singleObserver = new SingleObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(Object object) {
                long value = (long) object;
                Log.d("SINGLE_OPERATOR", "onSuccess: "+value);
                Log.d("SINGLE_OPERATOR", "onSuccess-10: "+Thread.currentThread().getName());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        };

        singleObservable.subscribe(singleObserver);
    }


    public List<StudentModel> getAllStudent() {
        Observable observable = Observable.fromCallable(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                //populateStudent();

                listOfStudents.clear();
                for (int i = 0; i < 10; i++) {
                    Log.d("THREAD_TESTING", "call: " + Thread.currentThread().getName());
                    StudentModel model = new StudentModel("Name: " + i, (i + 1));
                    listOfStudents.add(model);
                }
                return true;
            }
        }).subscribeOn(Schedulers.io());
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                StudentModel model = (StudentModel) o;
                listOfStudents.add(model);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        };
        observable.subscribe(observer);
        return listOfStudents;

    }

    private List<StudentModel> populateStudent() {
        listOfStudents.clear();
        for (int i = 0; i < 1000000; i++) {
            Toast.makeText(this, "Thread: " + Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
            StudentModel model = new StudentModel("Name: " + i, (i + 1));
            listOfStudents.add(model);
        }
        return listOfStudents;
    }
}