package com.github.jupittar.commlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BaseFragment extends Fragment {

  private Unbinder mUnbinder;
  private CompositeSubscription mCompositeSubscription;

  public CompositeSubscription getCompositeSubscription() {
    if (this.mCompositeSubscription == null) {
      this.mCompositeSubscription = new CompositeSubscription();
    }
    return this.mCompositeSubscription;
  }

  /**
   * Remembers to invoke this method to add the subscription to mCompositeSubscription after using it.
   */
  public void addSubscription(Subscription subscription) {
    getCompositeSubscription().add(subscription);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mUnbinder = ButterKnife.bind(this, view);
  }

  @Override
  public void onDestroyView() {
    mUnbinder.unbind();
    if (this.mCompositeSubscription != null) {
      this.mCompositeSubscription.unsubscribe();
    }
    super.onDestroyView();
  }
}
