<#include "/include/header.ftl">
<card title="${game_title}">
<p>
<#if curr_facility?exists>
<@a href="/p?mv_W/${pid}/${curr_facility.id}/"/>继续走</a><br/>
</#if>
撤退到${curr_facility.name}<br/>
金钱: -${monster_abort}(铜贝)<br/>
<#--士气: -${lost_morale}<br/>-->
<#if curr_facility?exists>
<@a href="/p?mv_W/${pid}/${curr_facility.id}/"/>继续走</a><br/>
</#if>
</p>
</card>
</wml>
