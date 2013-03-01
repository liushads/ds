<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
装备合成 <br/>
<#if !(label_compose_suit?exists && (label_compose_suit?size > 0) || label_compose?exists && (label_compose?size > 0))>
没有可以合成的装备<br/>
</#if>
<#if label_compose_suit?exists && (label_compose_suit?size > 0)>
<#list label_compose_suit as suit>
<@a href="/p?i_CI/${pid}/${suit.id}/"/>${suit.name}</a><br/>
</#list>
</#if>

<#if label_compose?exists && (label_compose?size > 0)>
<#list label_compose as compose>
<@a href="/p?i_CI/${pid}/${compose.id}/desc/"/>${compose.itemName}</a><br/>
</#list>
</#if>
<@gogame/>
<#include "/include/foot_card.ftl">