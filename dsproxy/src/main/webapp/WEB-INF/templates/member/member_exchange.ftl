<#include "/include/header.ftl">
<card title="${game_title}"><p>
会员积分换礼品<br/>
<#if playerMember?exists>
我的积分：${player.rewardPoints}分<br/>
礼品列表：<br/>
<#if page_objs?exists>
<#list page_objs as pitem>
<@a href="/p?p_m/${pid}/m_ex/${pitem.id}"/>${pitem.itemName}x${pitem.amount}</a>(${pitem.points}积分)<br/>
</#list>
<#if (page>0)><@a href="p?p_m/${pid}/m_e/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="p?p_m/${pid}/m_e/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
</#if>
<#else>
你还不是会员用户<br/>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>