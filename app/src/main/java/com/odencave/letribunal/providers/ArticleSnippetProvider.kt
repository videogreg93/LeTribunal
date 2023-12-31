package com.odencave.letribunal.providers

import com.odencave.letribunal.models.ArticleSnippetLT
import com.odencave.letribunal.models.CategoryLT
import com.odencave.letribunal.models.CategoryWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ArticleSnippetProvider {
    @GET("pf/api/v3/content/fetch/story-feed-query?query=%7B\"feature\"%3A\"top-table-list\"%2C\"query\"%3A\"type%3Astory%2BAND%20NOT%2Btaxonomy.primary_section._id%3A%5C\"%2Favis-publics%5C\"\"%2C\"size\"%3A5%7D&filter=%7Bcontent_elements%7B_id%2Ccredits%7Bby%7B_id%2Cadditional_properties%7Boriginal%7Bbyline%7D%7D%2Cname%2Ctype%2Curl%7D%7D%2Cdescription%7Bbasic%7D%2Cdisplay_date%2Cembed_html%2Cheadlines%7Bbasic%7D%2Clabel%7Baccessibilite%7Bdisplay%2Ctext%7D%2Cshow_video_icon%7Bdisplay%2Ctext%7D%7D%2Cpromo_items%7Bbasic%7Bcaption%2Cresized_params%7B274x154%2C274x183%2C274x206%2C377x212%2C377x251%2C377x283%2C400x225%2C400x267%2C400x300%2C600x338%2C600x400%2C600x450%2C800x450%2C800x533%2C800x600%7D%2Ctype%2Curl%7D%2Clead_art%7Bembed_html%2Cpromo_items%7Bbasic%7Bresized_params%7B274x154%2C274x183%2C274x206%2C377x212%2C377x251%2C377x283%2C400x225%2C400x267%2C400x300%2C600x338%2C600x400%2C600x450%2C800x450%2C800x533%2C800x600%7D%2Ctype%2Curl%7D%7D%2Ctype%7D%7D%2Csubtype%2Ctype%2Cwebsites%7Blatribune%7Bwebsite_section%7B_id%2Cname%7D%2Cwebsite_url%7D%7D%7D%7D&d=85&_website=latribune")
    fun fetchMostRecentArticles(): Call<ArticleSnippetLT>

    @GET("pf/api/v3/content/fetch/most-popular-clavis?query=%7B%22feature%22%3A%22top-table-list%22%2C%22size%22%3A5%7D&filter=%7Bcontent_elements%7B_id%2Ccredits%7Bby%7B_id%2Cadditional_properties%7Boriginal%7Bbyline%7D%7D%2Cname%2Ctype%2Curl%7D%7D%2Cdescription%7Bbasic%7D%2Cdisplay_date%2Cembed_html%2Cheadlines%7Bbasic%7D%2Clabel%7Baccessibilite%7Bdisplay%2Ctext%7D%2Cshow_video_icon%7Bdisplay%2Ctext%7D%7D%2Cpromo_items%7Bbasic%7Bcaption%2Cresized_params%7B274x154%2C274x183%2C274x206%2C377x212%2C377x251%2C377x283%2C400x225%2C400x267%2C400x300%2C600x338%2C600x400%2C600x450%2C800x450%2C800x533%2C800x600%7D%2Ctype%2Curl%7D%2Clead_art%7Bembed_html%2Cpromo_items%7Bbasic%7Bresized_params%7B274x154%2C274x183%2C274x206%2C377x212%2C377x251%2C377x283%2C400x225%2C400x267%2C400x300%2C600x338%2C600x400%2C600x450%2C800x450%2C800x533%2C800x600%7D%2Ctype%2Curl%7D%7D%2Ctype%7D%7D%2Csubtype%2Ctype%2Cwebsites%7Blatribune%7Bwebsite_section%7B_id%2Cname%7D%2Cwebsite_url%7D%7D%7D%7D&d=85&_website=latribune")
    fun fetchMostPopularArticles(): Call<ArticleSnippetLT>

    @GET("pf/api/v3/content/fetch/story-feed-sections?filter=%7Bcontent_elements%7B_id%2Ccredits%7Bby%7B_id%2Cadditional_properties%7Boriginal%7Bbyline%7D%7D%2Cname%2Ctype%2Curl%7D%7D%2Cdescription%7Bbasic%7D%2Cdisplay_date%2Cembed_html%2Cheadlines%7Bbasic%7D%2Clabel%7Baccessibilite%7Bdisplay%2Ctext%7D%2Cshow_video_icon%7Bdisplay%2Ctext%7D%7D%2Cpromo_items%7Bbasic%7Bcaption%2Cresized_params%7B274x154%2C274x183%2C274x206%2C377x212%2C377x251%2C377x283%2C400x225%2C400x267%2C400x300%2C600x338%2C600x400%2C600x450%2C800x450%2C800x533%2C800x600%7D%2Ctype%2Curl%7D%2Clead_art%7Bembed_html%2Cpromo_items%7Bbasic%7Bresized_params%7B274x154%2C274x183%2C274x206%2C377x212%2C377x251%2C377x283%2C400x225%2C400x267%2C400x300%2C600x338%2C600x400%2C600x450%2C800x450%2C800x533%2C800x600%7D%2Ctype%2Curl%7D%7D%2Ctype%7D%7D%2Csubtype%2Ctype%2Cwebsites%7Blatribune%7Bwebsite_section%7B_id%2Cname%7D%2Cwebsite_url%7D%7D%7D%7D&d=88&_website=latribune")
    fun fetchSection(@Query("query") query: String): Call<ArticleSnippetLT>

    @GET("pf/api/v3/content/fetch/site-service-hierarchy?query=%7B%22feature%22%3A%22header-nav-chain%22%2C%22hierarchy%22%3A%22sections-menu%22%7D&filter=%7Bchildren%7B_id%2Cchildren%7B_id%2Cdisplay_name%2Cname%2Cnode_type%2Curl%7D%2Cdisplay_name%2Cname%2Cnode_type%2Curl%7D%7D&d=88&_website=latribune")
    fun getAllCategories(): Call<CategoryWrapper> // TODO greg move to another provider
}