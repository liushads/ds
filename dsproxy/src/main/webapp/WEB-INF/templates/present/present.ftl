<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
<img alt="" src="/images/presents/${item.id}.gif"/>
${item.name}(<#if item.gold=0>${item.price/1000}银<#else>${(item.gold/100)?int}金</#if>)
<br/>
价值：${item.level}<br/>
${item.description}<br/>
有效期<#if item.gold &gt; 0>7<#else>3</#if>天。<br/>
增加密友度${item.intimateScore}<br/>
<#if back_present>
<@a href="/p?ps_M/${pid}/${label_receiver.id}"/>挑选礼物回送给<#if label_receiver.sex=0>她<#else>他</#if>吧</a>
<#else>
<@a href="/p?ps_BS/${pid}/${receiver.id}/${item.id}"/>送出</a>
</#if>
<br/>
<@goback />
<@gogame/>
</p>
</card>
</wml>

 