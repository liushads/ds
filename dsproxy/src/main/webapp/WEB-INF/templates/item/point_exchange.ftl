<#include "/include/header.ftl">
<card title="${game_title}"><p>
积分兑换<br/>
我的积分：${player.rewardPoints}分<br/>
礼品列表：<br/>
<#if page_objs?exists>
<#list page_objs as pitem>
<@a href="/p?i_PE/${pid}/e/${pitem.id}"/>${pitem.itemName}x${pitem.amount}</a>(${pitem.points}积分)<br/>
</#list>
<#if (page>0)><@a href="p?i_PE/${pid}/l/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="p?i_PE/${pid}/l/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>