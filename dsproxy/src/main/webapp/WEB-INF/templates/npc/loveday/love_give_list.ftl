<#include "/include/header.ftl">
<card title="${game_title}"><p>
<img alt="" src="${img_server}/lovedaytree.gif" /><br/>
情人节活动送祝福<br/>
玩家许愿列表：<br/>
<#if page_objs?exists>
<#list page_objs as obj>
 <@a href="/p?n_love/${pid}/choose/${obj.player.id}"/>${obj.player.name}:${obj.wishes}</a><br/>
</#list>
<#if (page>0)><@a href="/p?n_love/${pid}/give/${page-1}"/>上页.</a></#if>
<#if (page<total_page-1)><@a href="/p?n_love/${pid}/give/${page+1}"/>下页.</a></#if>
<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
</#if>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>