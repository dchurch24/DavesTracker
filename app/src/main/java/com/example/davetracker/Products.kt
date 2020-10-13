package com.example.davetracker
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
@JsonIgnoreProperties
data class Json4Kotlin_Base (

    val status : Int?,
    val product : Product?,
    val code : String?,
    val status_verbose : String?
)
/*
@JsonIgnoreProperties
data class _1 (

    val uploader : String,
    val sizes : Sizes,
    val uploaded_t : Int
)
@JsonIgnoreProperties
data class _2 (

    val uploader : String,
    val uploaded_t : Int,
    val sizes : Sizes
)
@JsonIgnoreProperties
data class _100 (

    val w : Int,
    val h : Int
)
@JsonIgnoreProperties
data class _200 (

    val w : Int,
    val h : Int
)

@JsonIgnoreProperties
data class _400 (

    val h : Int,
    val w : Int
)
 */
@JsonIgnoreProperties
data class Display (

    val en : String?
)
@JsonIgnoreProperties
data class Front (

    val small : Small?,
    val thumb : Thumb?,
    val display : Display?
)
@JsonIgnoreProperties
data class Front_en (

    val imgid : Int?,
    val y2 : Int?,
    val sizes : Sizes?,
    val y1 : Int?,
    val x2 : Int?,
    val white_magic : String?,
    val x1 : Int?,
    val geometry : String?,
    val rev : Int?,
    val normalize : String?,
    val angle : Int?
)
@JsonIgnoreProperties
data class Full (

    val w : Int?,
    val h : Int?
)
@JsonIgnoreProperties
data class Images (

    val front_en : Front_en?,
    val nutrition_en : Nutrition_en?
)
@JsonIgnoreProperties
data class Languages (

    val en_english : Int?
)
@JsonIgnoreProperties
data class Languages_codes (

    val en : Int?
)

@JsonIgnoreProperties
data class Nutriments (

    val fat_unit : String?,
    val energy_kcal_unit : String?,
    val sugars_100g : Int?,
    val energy_kcal : Int?,
    val carbohydrates_unit : String?,
    val sodium_100g : Double?,
    val carbohydrates_value : Int?,
    val salt_value : Double?,
    val salt : Double?,
    val proteins_unit : String?,
    val energy_100g : Int?,
    val energy : Int?,
    val proteins_value : Double?,
    val saturated_fat_100g : Double?,
    val sodium_value : Double?,
    val sugars_unit : String?,
    val salt_unit : String?,
    val carbohydrates_100g : Int?,
    val carbohydrates : Int?,
    val sugars_value : Int?,
    val fat : Double?,
    val salt_100g : Double?,
    val sodium : Double?,
    val sugars : Int?,
    val energy_kcal_value : Int?,
    val proteins_100g : Double?,
    val energy_unit : String?,
    val proteins : Double?,
    val saturated_fat_value : Double?,
    val energy_kcal_100g : Int?,
    val saturated_fat_unit : String?,
    val fat_100g : Double?,
    val saturated_fat : Double?,
    val sodium_unit : String?,
    val fat_value : Double?,
    val energy_value : Int?
)
@JsonIgnoreProperties
data class Nutrition (

    val thumb : Thumb?,
    val small : Small?,
    val display : Display?
)
@JsonIgnoreProperties
data class Nutrition_en (

    val x2 : Int?,
    val y1 : Int?,
    val white_magic : String?,
    val x1 : Int?,
    val geometry : String?,
    val rev : Int?,
    val normalize : String?,
    val angle : Int?,
    val imgid : Int?,
    val y2 : Int?,
    val sizes : Sizes?
)

@JsonIgnoreProperties
data class Product (

    val countries : String?,
    val languages : Languages?,
    val states : String?,
    val data_quality_tags : List<String>?,
    val states_hierarchy : List<String>?,
    val image_nutrition_url : String?,
    val traces : String?,
    val data_quality_info_tags : List<String>?,
    val id : String?,
    val selected_images : Selected_images?,
    val languages_hierarchy : List<String>?,
    val nutrition_data_prepared_per : String?,
    val pnns_groups_1_tags : List<String>?,
    val image_url : String?,
    val max_imgid : Int?,
    val traces_from_user : String?,
    val interface_version_modified : String?,
    val traces_from_ingredients : String?,
    val rev : Int?,
    val created_t : Int?,
    val pnns_groups_2_tags : List<String>?,
    val nutrition_data_per : String?,
    val allergens : String?,
    val allergens_from_ingredients : String?,
    //val categories_properties : Categories_properties,
    val pnns_groups_2 : String?,
    val states_tags : List<String>?,
    val image_front_thumb_url : String?,
    val completeness : Double?,
    val photographers_tags : List<String>?,
    val checkers_tags : List<String>?,
    val informers_tags : List<String>?,
    val last_image_t : Int?,
    val categories_properties_tags : List<String>?,
    val __keywordswords : List<String>?,
    val nutrient_levels_tags : List<String>?,
    val last_modified_by : String?,
    val allergens_hierarchy : List<String>?,
    val popularity_popularity_key : Int?,
    val allergens_tags : List<String>?,
    val pnns_groups_1 : String?,
    val data_quality_bugs_tags : List<String>?,
    val countries_tags : List<String>?,
    val traces_tags : List<String>?,
    val correctors_tags : List<String>?,
    val product_name_en : String?,
    val image_front_url : String?,
    val image_small_url : String?,
    val last_image_dates_tags : List<String>?,
    val data_sources : String?,
    val lang : String?,
    val data_quality_warnings_tags : List<String>?,
    val creator : String?,
    val sortsortkey : Int?,
    val unknown_nutrients_tags : List<String>?,
    val product_name : String?,
    val image_nutrition_thumb_url : String?,
    val nutrition_score_debug : String?,
    val entry_dates_tags : List<String>?,
    val codes_tags : List<String>?,
    val nova_group_debug : String?,
    val languages_tags : List<String>?,
    val lc : String?,
    val code : String?,
    val misc_tags : List<String>?,
    val editors_tags : List<String>?,
    //val nutrient_levels : Nutrient_levels,
    val last_editor : String?,
    val traces_hierarchy : List<String>?,
    val last_modified_t : Int?,
    val nutrition_grades_tags : List<String>?,
    val languages_codes : Languages_codes?,
    val _id : String?,
    val data_sources_tags : List<String>?,
    val image_nutrition_small_url : String?,
    val update_update_key : String?,
    val interface_version_created : String?,
    val data_quality_errors_tags : List<String>?,
    val countries_hierarchy : List<String>?,
    val nutriments : Nutriments?,
    val nova_group_tags : List<String>?,
    val images : Images?,
    val last_edit_dates_tags : List<String>?,
    val allergens_from_user : String?,
    val complete : Int?,
    val image_thumb_url : String?,
    val image_front_small_url : String?
)

@JsonIgnoreProperties
data class Selected_images (

    val front : Front?,
    val nutrition : Nutrition?
)

@JsonIgnoreProperties
data class Sizes (

    //val _400 : _400,
    //val _100 : _100,
    val full : Full?
)
@JsonIgnoreProperties
data class Small (

    val en : String?
)
@JsonIgnoreProperties
data class Thumb (

    val en : String?
)























































