<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if items?exists>
<#list items as list>
<#if list_index?exists&&list_index==3>
<br/>
</#if>
<#if list_index?exists&&list_index==5>
<br/>
</#if>
<#if list_index?exists&&list_index==9>
<br/>
</#if>
<#if list_index?exists&&list_index==4>
<#if item.id==10272>
${5000*num?number}经验   |
<#elseif item.id==10407>
   100 银 币                 |
<#else>
${item.name}|
</#if>
</#if>
<#if list.id==10272>
${5000*num?number}经验   |
<#elseif list.id==10407>
  100 银 币                 |
<#else>
${list.name}|
</#if>
</#list>
</#if>
<br/>
若您确认就选中间的物品【
<#if item.id==10272>
${5000*num?number}经验
<#elseif item.id==10407>
  100 银 币
<#else>
${item.name}
</#if>】，转动宝箱您可以再获得一件宝箱物品【温馨提示：不点击确定和关闭页面无法获得任何物品！】
<br/>
<@a href="/p?i_X/${pid}/${item.id}/${num}/${player_item}"/>确定</a><br/>
<@a href="/p?i_HTC/${pid}/${num}/${item.id}/${player_item}"/>加1金让宝箱转动（只能转动1次）</a><br/>
</p>
<@goback/>
</card>
</wml>