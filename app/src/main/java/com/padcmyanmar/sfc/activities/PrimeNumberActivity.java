package com.padcmyanmar.sfc.activities;

import android.arch.persistence.room.util.StringUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.padcmyanmar.sfc.R;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class PrimeNumberActivity extends AppCompatActivity {

    @BindView(R.id.edt_prime_input)
    EditText mNumberField;

    @BindView(R.id.tv_prime_result)
    TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_number);
        ButterKnife.bind(this, this);

        mNumberField.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
               searchQuery(v.getText().toString());
               v.setText("");
                handled = true;
            }
            return handled;
        });


    }

    private void searchQuery(String s) {
        if (TextUtils.isEmpty(s)) {
            return;
        }

        Single<String> singleObservable = Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return generatePrimeNumbers(Integer.parseInt(s));
            }
        });

        singleObservable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull String s) {
                        mResultTextView.setText(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }


    private void testRxBinding(){
        RxTextView.textChanges(mNumberField)
                .filter(text -> !text.toString().isEmpty())
                .debounce(500, TimeUnit.MILLISECONDS)
                .switchMap(new Function<CharSequence, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(CharSequence charSequence) throws Exception {
                        return Observable.fromCallable(() -> generatePrimeNumbers(Integer.parseInt(charSequence.toString())));
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharSequence>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        mResultTextView.setText(charSequence);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    private String generatePrimeNumbers(int count) {
        final StringBuilder builder = new StringBuilder();
        int number = 2;
        int primeCount = 0;
        while (true) {
            boolean isPrime = true;

            for (int i = 2; i < number; i++) {
                if (number % i == 0) {
                    isPrime = false;
                }
            }
            if (isPrime) {
                primeCount++;
                builder.append(number);
                if (primeCount < count) {
                    builder.append(", ");
                } else {
                    break;
                }
            }
            number++;

        }

        return builder.toString();
    }


}
