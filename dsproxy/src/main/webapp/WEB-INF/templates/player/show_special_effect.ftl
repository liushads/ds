<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
【特性|<@a href="/p?p_VSP/${pid}/res/"/>抗性</a>|<@a href="/p?p_LL/${pid}/life/"/>时效</a>】<br/>
破甲几率：${player.dyn.sunder}<#if player.dyn.sunder != 0>%</#if><br/>
破甲效果：${player.dyn.extraSunder}<br/>
反震几率：${player.dyn.rebound}<#if player.dyn.rebound != 0>%</#if><br/>
反震效果：${player.dyn.extraRebound}<br/>
封印几率：${player.dyn.seal}<#if player.dyn.seal != 0>%</#if><br/>
封印效果：${player.dyn.extraSeal}<br/>
中毒几率：${player.dyn.poison}<#if player.dyn.poison != 0>%</#if><br/>
中毒效果：${player.dyn.extraPoison}<br/>
破魂几率：${player.dyn.brokensoul}<#if player.dyn.brokensoul != 0>%</#if><br/>
破魂效果：${player.dyn.extraBrokensoul}<br/>
迟缓几率：${player.dyn.slow}<#if player.dyn.slow != 0>%</#if><br/>
迟缓效果：${player.dyn.extraSlow}<br/>
<@a href="/p?p_VSP/${pid}/info/"/>特性说明</a><br/>
<@goback />
<@gofacility/>
</p></card>
</wml> 