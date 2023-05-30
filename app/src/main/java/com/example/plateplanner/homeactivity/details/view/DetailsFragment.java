package com.example.plateplanner.homeactivity.details.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.plateplanner.R;
import com.example.plateplanner.datebase.ConcreteLocalSource;
import com.example.plateplanner.homeactivity.details.model.Ingredient;
import com.example.plateplanner.homeactivity.details.presenter.DetailsFragmentPresenter;
import com.example.plateplanner.network.ApiClient;
import com.example.plateplanner.network.FirebaseCalls;
import com.example.plateplanner.model.AuthSharedPreferences;
import com.example.plateplanner.model.MealPojo;
import com.example.plateplanner.model.PlanMeal;
import com.example.plateplanner.model.Repository;
import com.example.plateplanner.startactivity.view.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;


public class DetailsFragment extends Fragment implements DetailsFragmentViewInterface {

    private final String TAG = "DetailsFragment";
    MealPojo mealPojo;
    boolean clicked = false;

    CircleImageView mealImage;
    TextView mealName;
    TextView mealCountry;
    TextView steps;
    ImageButton favoriteBtn;
    ImageButton addToPlanBtn;
    ImageButton shareToCalenderBtn;
    ImageButton backBtn;
    RecyclerView ingredientRv;
    YouTubePlayerView playerView;
    ArrayList<Ingredient> ingredientList = new ArrayList<>();
    boolean state = false;
    DetailsFragmentPresenter detailsFragmentPresenter;
    private int mSelectedIndex = 0;
    private PlanMeal planMeal;

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
        detailsFragmentPresenter = new DetailsFragmentPresenter(this, Repository.getInstance(AuthSharedPreferences.getInstance(getContext()), FirebaseCalls.getInstance(), ApiClient.getInstance(), ConcreteLocalSource.getInstance(getContext())));
        DetailsFragmentArgs detailsFragmentArgs = DetailsFragmentArgs.fromBundle(getArguments());
        mealPojo = detailsFragmentArgs.getMeal();
        planMeal = new PlanMeal(mealPojo.getIdMeal(), mealPojo.getMealName(), mealPojo.getStrDrinkAlternate(), mealPojo.getStrCategory(), mealPojo.getStrArea(), mealPojo.getStrInstructions(), mealPojo.getStrMealThumb(), mealPojo.getStrTags(), mealPojo.getStrYoutube(), mealPojo.getStrIngredient1(), mealPojo.getStrIngredient2(), mealPojo.getStrIngredient3(), mealPojo.getStrIngredient4(), mealPojo.getStrIngredient5(), mealPojo.getStrIngredient6(), mealPojo.getStrIngredient7(), mealPojo.getStrIngredient8(), mealPojo.getStrIngredient9(), mealPojo.getStrIngredient10(), mealPojo.getStrIngredient11(), mealPojo.getStrIngredient12(), mealPojo.getStrIngredient13(), mealPojo.getStrIngredient14(), mealPojo.getStrIngredient15(), mealPojo.getStrIngredient16(), mealPojo.getStrIngredient17(), mealPojo.getStrIngredient18(), mealPojo.getStrIngredient19(), mealPojo.getStrIngredient20(), mealPojo.getStrMeasure1(), mealPojo.getStrMeasure2(), mealPojo.getStrMeasure3(), mealPojo.getStrMeasure4(), mealPojo.getStrMeasure5(), mealPojo.getStrMeasure6(), mealPojo.getStrMeasure7(), mealPojo.getStrMeasure8(), mealPojo.getStrMeasure9(), mealPojo.getStrMeasure10(), mealPojo.getStrMeasure11(), mealPojo.getStrMeasure12(), mealPojo.getStrMeasure13(), mealPojo.getStrMeasure14(), mealPojo.getStrMeasure15(), mealPojo.getStrMeasure16(), mealPojo.getStrMeasure17(), mealPojo.getStrMeasure18(), mealPojo.getStrMeasure19(), mealPojo.getStrMeasure20(), mealPojo.getStrSource(), mealPojo.getStrImageSource(), mealPojo.getStrCreativeCommonsConfirmed(), mealPojo.getDateModified(), "");

        initUi(view);
        detailsFragmentPresenter.checkExistence(mealPojo.getIdMeal()).observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != null) {
                    if (aBoolean) {
                        favoriteBtn.setImageResource(R.drawable.solid_heart_icon);
                        clicked = true;
                    }
                }
            }
        });

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

    private ArrayList<Ingredient> createIngredientList(MealPojo mealPojo) {
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        if (mealPojo != null) {
            if ((mealPojo.getStrIngredient1() != null) && !mealPojo.getStrIngredient1().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient1(), mealPojo.getStrMeasure1()));
            }
            if ((mealPojo.getStrIngredient2() != null) && !mealPojo.getStrIngredient2().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient2(), mealPojo.getStrMeasure2()));
            }
            if ((mealPojo.getStrIngredient3() != null) && !mealPojo.getStrIngredient3().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient3(), mealPojo.getStrMeasure3()));
            }
            if ((mealPojo.getStrIngredient4() != null) && !mealPojo.getStrIngredient4().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient4(), mealPojo.getStrMeasure4()));
            }
            if ((mealPojo.getStrIngredient5() != null) && !mealPojo.getStrIngredient5().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient5(), mealPojo.getStrMeasure5()));
            }
            if ((mealPojo.getStrIngredient6() != null) && !mealPojo.getStrIngredient6().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient6(), mealPojo.getStrMeasure6()));
            }
            if ((mealPojo.getStrIngredient7() != null) && !mealPojo.getStrIngredient7().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient7(), mealPojo.getStrMeasure7()));
            }
            if ((mealPojo.getStrIngredient8() != null) && !mealPojo.getStrIngredient8().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient8(), mealPojo.getStrMeasure8()));
            }
            if ((mealPojo.getStrIngredient9() != null) && !mealPojo.getStrIngredient9().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient9(), mealPojo.getStrMeasure9()));
            }
            if ((mealPojo.getStrIngredient10() != null) && !mealPojo.getStrIngredient10().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient10(), mealPojo.getStrMeasure10()));
            }
            if ((mealPojo.getStrIngredient11() != null) && !mealPojo.getStrIngredient11().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient11(), mealPojo.getStrMeasure11()));
            }
            if ((mealPojo.getStrIngredient12() != null) && !mealPojo.getStrIngredient12().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient12(), mealPojo.getStrMeasure12()));
            }
            if ((mealPojo.getStrIngredient13() != null) && !mealPojo.getStrIngredient13().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient13(), mealPojo.getStrMeasure13()));
            }
            if ((mealPojo.getStrIngredient14() != null) && !mealPojo.getStrIngredient14().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient14(), mealPojo.getStrMeasure14()));
            }
            if ((mealPojo.getStrIngredient15() != null) && !mealPojo.getStrIngredient15().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient15(), mealPojo.getStrMeasure15()));
            }
            if ((mealPojo.getStrIngredient16() != null) && !mealPojo.getStrIngredient16().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient16(), mealPojo.getStrMeasure16()));
            }
            if ((mealPojo.getStrIngredient17() != null) && !mealPojo.getStrIngredient17().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient17(), mealPojo.getStrMeasure17()));
            }
            if ((mealPojo.getStrIngredient18() != null) && !mealPojo.getStrIngredient18().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient18(), mealPojo.getStrMeasure18()));
            }
            if ((mealPojo.getStrIngredient19() != null) && !mealPojo.getStrIngredient19().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient19(), mealPojo.getStrMeasure19()));
            }
            if ((mealPojo.getStrIngredient20() != null) && !mealPojo.getStrIngredient20().isEmpty()) {
                ingredientList.add(new Ingredient(mealPojo.getStrIngredient20(), mealPojo.getStrMeasure20()));
            }
        }

        return ingredientList;
    }

    private void initUi(View view) {
        mealImage = view.findViewById(R.id.mealImage);
        mealName = view.findViewById(R.id.mealNameTv);
        mealCountry = view.findViewById(R.id.mealCountryTv);
        steps = view.findViewById(R.id.stepsTv);
        favoriteBtn = view.findViewById(R.id.addToFavoriteDetailsBtn);
        addToPlanBtn = view.findViewById(R.id.addToPlanBtn);
        shareToCalenderBtn = view.findViewById(R.id.shareToCalender);
        playerView = view.findViewById(R.id.videoPlayer);
        backBtn = view.findViewById(R.id.detailsBackButton);
        ingredientRv = view.findViewById(R.id.ingredientRv);
        getLifecycle().addObserver(playerView);
        ingredientList = createIngredientList(mealPojo);

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getContext(), ingredientList);
        ingredientRv.setAdapter(ingredientsAdapter);

        listeners();
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
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    if (!clicked) {
                        addMealToFavorites(mealPojo);
                        favoriteBtn.setImageResource(R.drawable.solid_heart_icon);
                        clicked = true;
                    } else {
                        removeMealFromFavorites(mealPojo);
                        favoriteBtn.setImageResource(R.drawable.border_heart_icon);
                        clicked = false;
                    }
                } else {
                    Snackbar.make(getView(), "You need to signup first", Snackbar.LENGTH_SHORT).setAction("Sign up", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Add code here to handle the button click event
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                        }
                    }).show();
                }
            }
        });

        addToPlanBtn.setOnClickListener(view -> {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                showDialog();
            } else {
                Snackbar.make(getView(), "You need to signup first", Snackbar.LENGTH_SHORT).setAction("Sign up", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }
                }).show();
            }
        });

        shareToCalenderBtn.setOnClickListener(view -> {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                Calendar beginTime = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, mealPojo.getMealName())
                        .putExtra(CalendarContract.Events.DESCRIPTION, mealPojo.getStrTags());

                startActivity(intent);
            } else {
                Snackbar.make(getView(), "You need to signup first", Snackbar.LENGTH_SHORT).setAction("Sign up", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }
                }).show();
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

    @Override
    public void addMealToFavorites(MealPojo mealPojo) {
        detailsFragmentPresenter.addMealToFavourites(mealPojo);
    }

    @Override
    public void removeMealFromFavorites(MealPojo mealPojo) {
        detailsFragmentPresenter.removeMealFromFavourites(mealPojo);
    }

    private void showDialog() {
        List<String> daysOfWeek = Arrays.asList("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_single_choice, daysOfWeek);

        new AlertDialog.Builder(getContext())
                .setTitle("Select a day of the week")
                .setSingleChoiceItems(adapter, 0, (dialog, which) -> mSelectedIndex = which)
                .setPositiveButton("OK", (dialog, which) -> {
                    if (mSelectedIndex >= 0) {
                        String selectedDay = daysOfWeek.get(mSelectedIndex);
                        Toast.makeText(getContext(), selectedDay, Toast.LENGTH_SHORT).show();
                        planMeal.setDay(selectedDay);
                        detailsFragmentPresenter.addToPlan(planMeal);
                    }
                })
                .setNegativeButton("Cancel", null).show();
    }
}