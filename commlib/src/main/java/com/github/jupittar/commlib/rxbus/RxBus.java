package com.github.jupittar.commlib.rxbus;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * A simple event bus implemented by RxJava
 * <p>
 * Created by Jupittar on 2016/4/29.
 */
public class RxBus {

  private final Subject<Object, Object> mBus = new SerializedSubject<>(PublishSubject.create());

  private RxBus() {

  }

  public static RxBus getDefault() {
    return SingletonHolder.INSTANCE;
  }

  public void post(String tag, Object o) {
    if (mBus.hasObservers()) {
      mBus.onNext(new BusEvent(tag, o));
    }
  }

  @SuppressWarnings("unchecked")
  public <T> Observable<T> toObservable(final String tag, final Class<T> tClass) {
    return mBus.filter(new Func1<Object, Boolean>() {
      @Override
      public Boolean call(Object o) {
        if (!(o instanceof BusEvent)) {
          return false;
        }
        BusEvent busEvent = (BusEvent) o;
        return tClass.isInstance(busEvent.getObject()) && tag != null && tag.equals(busEvent.getTag());
      }
    }).map(new Func1<Object, T>() {
      @Override
      public T call(Object o) {
        BusEvent busEvent = (BusEvent) o;
        return (T) busEvent.getObject();
      }
    });
  }

  private static class SingletonHolder {
    static final RxBus INSTANCE = new RxBus();
  }

}
