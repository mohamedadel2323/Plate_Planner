package com.example.plateplanner.homeactivity.details.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.plateplanner.R;
import com.example.plateplanner.startactivity.model.MealPojo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;


public class DetailsFragment extends Fragment {

    private final String TAG = "DetailsFragment";
    MealPojo mealPojo;
    boolean clicked = false;

    CircleImageView mealImage;
    TextView mealName;
    TextView mealCountry;
    TextView steps;
    ImageButton favoriteBtn;
    ImageButton calenderBtn;
    ImageButton backBtn;
    RecyclerView ingredientRv;
    YouTubePlayerView playerView;
    ArrayList<String> ingredientList = new ArrayList<>();


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DetailsFragmentArgs detailsFragmentArgs = DetailsFragmentArgs.fromBundle(getArguments());
        mealPojo = detailsFragmentArgs.getMeal();


        initUi(view);
        listeners();

        ingredientList = createIngredientList(mealPojo);

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getContext(), ingredientList);

        ingredientRv.setAdapter(ingredientsAdapter);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setVisibility(View.GONE);
        }
        Glide.with(getContext())
                .load(mealPojo.getStrMealThumb())
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.ic_broken_image)
                .into(mealImage);
        mealName.setText(mealPojo.getMealName());
        mealCountry.setText(mealPojo.getStrArea());
        steps.setText(mealPojo.getStrInstructions());
        Log.e(TAG, mealPojo.toString());
    }

    private ArrayList<String> createIngredientList(MealPojo mealPojo) {
        ArrayList<String> ingredientList = new ArrayList<>();
        if (!mealPojo.getStrIngredient1().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient1());
        }
        if (!mealPojo.getStrIngredient2().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient2());
        }
        if (!mealPojo.getStrIngredient3().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient3());
        }
        if (!mealPojo.getStrIngredient4().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient4());
        }
        if (!mealPojo.getStrIngredient5().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient5());
        }
        if (!mealPojo.getStrIngredient6().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient6());
        }
        if (!mealPojo.getStrIngredient7().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient7());
        }
        if (!mealPojo.getStrIngredient8().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient8());
        }
        if (!mealPojo.getStrIngredient9().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient9());
        }
        if (!mealPojo.getStrIngredient10().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient10());
        }
        if (!mealPojo.getStrIngredient11().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient11());
        }
        if (!mealPojo.getStrIngredient12().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient12());
        }
        if (!mealPojo.getStrIngredient13().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient13());
        }
        if (!mealPojo.getStrIngredient14().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient14());
        }
        if (!mealPojo.getStrIngredient15().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient15());
        }
        if (!mealPojo.getStrIngredient16().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient16());
        }
        if (!mealPojo.getStrIngredient17().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient17());
        }
        if (!mealPojo.getStrIngredient18().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient18());
        }
        if (!mealPojo.getStrIngredient19().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient19());
        }
        if (!mealPojo.getStrIngredient20().isEmpty()) {
            ingredientList.add(mealPojo.getStrIngredient20());
        }
        return ingredientList;
    }

    private void initUi(View view) {
        mealImage = view.findViewById(R.id.mealImage);
        mealName = view.findViewById(R.id.mealNameTv);
        mealCountry = view.findViewById(R.id.mealCountryTv);
        steps = view.findViewById(R.id.stepsTv);
        favoriteBtn = view.findViewById(R.id.addToFavoriteDetailsBtn);
        calenderBtn = view.findViewById(R.id.addToPlanBtn);
        playerView = view.findViewById(R.id.videoPlayer);
        backBtn = view.findViewById(R.id.detailsBackButton);
        ingredientRv = view.findViewById(R.id.ingredientRv);
        getLifecycle().addObserver(playerView);
    }

    private void listeners() {
        if (!mealPojo.getStrYoutube().isEmpty()) {
            playerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = extractVideoIdFromUrl(mealPojo.getStrYoutube());
                    youTubePlayer.loadVideo(videoId, 0);
                    youTubePlayer.pause();
                }
            });
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clicked) {
                    favoriteBtn.setImageResource(R.drawable.solid_heart_icon);
                    clicked = true;
                } else {
                    favoriteBtn.setImageResource(R.drawable.border_heart_icon);
                    clicked = false;
                }
            }
        });

    }

    public static String extractVideoIdFromUrl(String url) {
        String videoId = null;
        Pattern pattern = Pattern.compile("(?<=v(=|/))([\\w-]+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            videoId = matcher.group();
        }
        return videoId;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }
}