package fr.systemathic.mvvm_simple_example.bindings;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.databinding.BindingAdapter;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import fr.systemathic.mvvm_simple_example.R;

public class CustomViewBindings {

    @BindingAdapter("setAdapter")
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView,
                                               RecyclerView.Adapter<?> adapter){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(),2));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter(("onRefresh"))
    public static void onRefresh(SwipeRefreshLayout refreshLayout, final SwipeRefreshLayout.OnRefreshListener listener){
        refreshLayout.setOnRefreshListener(listener);
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url){
        if(url != null){
            Picasso.get().load(url).into(imageView);
        }else{
            imageView.setImageResource(R.drawable.cross);
        }
    }

    ///////// RV ITEMS

    @BindingAdapter("id_text")
    public static void bindIdText(TextView textView, String text){
        textView.setText(String.format("ID : %s",text));
    }

    @BindingAdapter("substringText")
    public static void bindTitleText(TextView textView, String text){
        String txt = (text.length()<=50) ? text : String.format("%s...",text.substring(0,50));
        textView.setText(txt);
    }

    ///////////

    @BindingAdapter("scaleAnimation")
    public static void scaleViewAnimation(View view, int startDelay){
        view.setScaleX(0);
        view.setScaleY(0);
        view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(startDelay)
                .setDuration(350)
                .start();
    }

    @BindingAdapter("alphaAnimation")
    public static void alphaViewAnimation(View view, int startDelay){
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(1000);
        animation.setStartOffset(startDelay);
        view.startAnimation(animation);
    }
}