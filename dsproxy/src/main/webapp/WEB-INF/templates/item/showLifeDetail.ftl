<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if (item?exists)>
名称：${item.item.name}<br/>
<#if item.item.attackMax &gt;0>
最大攻击： ${item.item.attackMax}<br/>
</#if>
<#if item.item.attackMin &gt;0>
最小攻击：${item.item.attackMin}<br/>
</#if>
<#if item.item.exp &gt;0>
 经验倍数：${1+item.item.exp/100}<br/>
 </#if>
 <#if item.item.defence &gt;0>
 防卫：${item.item.defence}<br/>
  </#if>
  <#if item.item.agility &gt;0>
 敏捷：${item.item.agility}<br/>
 </#if>
 <#if item.item.level &gt;0>
 级别：${item.item.level}<br/>
  </#if>
 有效期：${item.leftStr}<br/>
 <#else>
道具已过期.<br/>
 </#if> 
<@goback/> 
<@gogame/>
</p></card>
</wml>