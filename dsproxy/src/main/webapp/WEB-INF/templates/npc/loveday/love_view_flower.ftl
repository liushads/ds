<#include "/include/header.ftl">
<card title="${game_title}"><p>
情人节活动玫瑰园<br/>
种花玩家列表：<br/>
<#if page_objs?exists && page_objs?size &gt; 0>
<#list page_objs as obj>
 <@a href="/p?p_VO/${pid}/${obj.id}"/>${obj.name}</a>:<@a href="/p?n_love/${pid}/water/${obj.id}"/>浇水</a><br/>
</#list>
<#if (page>0)><@a href="/p?n_love/${pid}/view/${page-1}"/>上页.</a></#if>
<#if (page<total_page-1)><@a href="/p?n_love/${pid}/view/${page+1}"/>下页.</a></#if>
<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
<#else>
无人种花<br/>
</#if>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>