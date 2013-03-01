<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if location?exists>
${location}<br/>
</#if>
<#if lastNpc?exists>
${lastNpc.name}:${lastNpc.description}<br/>
</#if>
合成劳动宝箱<br/>
<#if player.level &lt; 51 && player.level &gt; 39>
您等级阶段为40-51合成的劳动宝箱打开可以获得：游龙装备箱+宝箱宝图*1，确定要合成吗？<br/>
<#elseif player.level &lt; 71 && player.level &gt; 50>
您等级阶段为51-70合成的劳动宝箱打开可以获得：赤血装备箱+宝箱宝图*1，确定要合成吗？<br/>
<#elseif player.level &lt; 91 && player.level &gt; 70>
您等级阶段为71-90合成的劳动宝箱打开可以获得：鬼神装备箱+宝箱宝图*1，确定要合成吗？<br/>
<#elseif player.level &gt; 90>
您等级阶段为91级段合成的劳动宝箱打开可以获得：逐风装备箱+宝箱宝图*1，确定要合成吗？<br/>
</#if>
<@a href="/p?n_Wy/${pid}/f/c"/>确定合成</a><br/>
<br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>