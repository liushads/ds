<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if location?exists>
${location}<br/>
</#if>
你点破了${number}号泡泡，恭喜你获得<#if item?exists>${item.name}<#else><#if (player.level > 50)>10000经验奖励<#else>5000经验奖励</#if></#if><br/>
<@a href="/p?n_Ly/${pid}/liuyi/"/>继续点泡泡</a> <br/>
看看其他泡泡里的奖品<br/>
<#if itemList?exists>
<#list itemList as itemL>		
	${itemL_index+ 1} . <#if itemL?exists><@a href="/p?i_IS/${pid}/${itemL.id}/"/>${itemL.name}</a><br/><#else><#if (player.level > 50)>10000经验奖励<br/><#else>5000经验奖励<br/></#if></#if>
</#list>
</#if>
<br/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>