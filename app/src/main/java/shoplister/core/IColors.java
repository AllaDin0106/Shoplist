package shoplister.core;

import shoplister.R;

public interface IColors
{
    default String asKey(String key)
    {
        return key.replace(' ', '-').toLowerCase();
    }

    default int foregroundColor(String category)
    {
        int id = R.color.Black;

        if (category.equals("bakery")) id = R.color.color_bakery;
        if (category.equals("berries")) id = R.color.color_berries;
        if (category.equals("deserts")) id = R.color.color_deserts;
        if (category.equals("dishes")) id = R.color.color_dishes;
        if (category.equals("drinks")) id = R.color.color_drinks;
        if (category.equals("fast-food")) id = R.color.color_fast_food;
        if (category.equals("fruits")) id = R.color.color_fruits;
        if (category.equals("ingredients")) id = R.color.color_ingredients;
        if (category.equals("meat")) id = R.color.color_meat;
        if (category.equals("nuts")) id = R.color.color_nuts;
        if (category.equals("pastry")) id = R.color.color_pastry;
        if (category.equals("seafood")) id = R.color.color_seafood;
        if (category.equals("vegetables")) id = R.color.color_vegetables;

        return id;
    }

    default int backgroundColor(String category)
    {
        int id = R.color.White;

        if (category.equals("bakery")) id = R.color.color_bakery_bg;
        if (category.equals("berries")) id = R.color.color_berries_bg;
        if (category.equals("deserts")) id = R.color.color_deserts_bg;
        if (category.equals("dishes")) id = R.color.color_dishes_bg;
        if (category.equals("drinks")) id = R.color.color_drinks_bg;
        if (category.equals("fast-food")) id = R.color.color_fast_food_bg;
        if (category.equals("fruits")) id = R.color.color_fruits_bg;
        if (category.equals("ingredients")) id = R.color.color_ingredients_bg;
        if (category.equals("meat")) id = R.color.color_meat_bg;
        if (category.equals("nuts")) id = R.color.color_nuts_bg;
        if (category.equals("pastry")) id = R.color.color_pastry_bg;
        if (category.equals("seafood")) id = R.color.color_seafood_bg;
        if (category.equals("vegetables")) id = R.color.color_vegetables_bg;

        return id;
    }

    default int borderColor(String category)
    {
        int id = R.color.White;

        if (category.equals("bakery")) id = R.drawable.border_bakery;
        if (category.equals("berries")) id = R.drawable.border_berries;
        if (category.equals("deserts")) id = R.drawable.border_deserts;
        if (category.equals("dishes")) id = R.drawable.border_dishes;
        if (category.equals("drinks")) id = R.drawable.border_drinks;
        if (category.equals("fast-food")) id = R.drawable.border_fast_food;
        if (category.equals("fruits")) id = R.drawable.border_fruits;
        if (category.equals("ingredients")) id = R.drawable.border_ingredients;
        if (category.equals("meat")) id = R.drawable.border_meat;
        if (category.equals("nuts")) id = R.drawable.border_nuts;
        if (category.equals("pastry")) id = R.drawable.border_pastry;
        if (category.equals("seafood")) id = R.drawable.border_seafood;
        if (category.equals("vegetables")) id = R.drawable.border_vegetables;

        return id;
    }

    default int borderColorChecked(String category)
    {
        int id = R.color.White;

        if (category.equals("bakery")) id = R.drawable.border_bakery_checked;
        if (category.equals("berries")) id = R.drawable.border_berries_checked;
        if (category.equals("deserts")) id = R.drawable.border_deserts_checked;
        if (category.equals("dishes")) id = R.drawable.border_dishes_checked;
        if (category.equals("drinks")) id = R.drawable.border_drinks_checked;
        if (category.equals("fast-food")) id = R.drawable.border_fast_food_checked;
        if (category.equals("fruits")) id = R.drawable.border_fruits_checked;
        if (category.equals("ingredients")) id = R.drawable.border_ingredients_checked;
        if (category.equals("meat")) id = R.drawable.border_meat_checked;
        if (category.equals("nuts")) id = R.drawable.border_nuts_checked;
        if (category.equals("pastry")) id = R.drawable.border_pastry_checked;
        if (category.equals("seafood")) id = R.drawable.border_seafood_checked;
        if (category.equals("vegetables")) id = R.drawable.border_vegetables_checked;

        return id;
    }
}
