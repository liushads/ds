<#include "/include/header.ftl">
<card title="${game_title}">
<p>
${location}<br/>
行政官:${city}<#if !city_tong?exists>
，守卫${city_guard}<br/>
<#else>
属于【${city_tong.name}】，帮主【${tong_admin}】<br/>
<@a href="/p?t_V/${pid}/${city_tong.id}"/>了解【${city_tong.name}】</a><br/>
</#if>
<@goback/>
<@gofacility/>
</p>
</card>
</wml>
