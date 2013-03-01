<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
【<@a href="/p?p_VSP/${pid}/effect/"/>特性</a>|抗性|<@a href="/p?p_LL/${pid}/0/"/>时效</a>】<br/>
破甲闪避：${player.dyn.antiSunder}<#if player.dyn.sunder != 0>%</#if><br/>
破甲抵抗：${player.dyn.extraAntiSunder}<br/>
反震闪避：${player.dyn.antiRebound}<#if player.dyn.rebound != 0>%</#if><br/>
反震抵抗：${player.dyn.extraAntiRebound}<br/>
封印闪避：${player.dyn.antiSeal}<#if player.dyn.seal != 0>%</#if><br/>
封印抵抗：${player.dyn.extraAntiSeal}<br/>
中毒闪避：${player.dyn.antiPoison}<#if player.dyn.poison != 0>%</#if><br/>
中毒抵抗：${player.dyn.extraAntiPoison}<br/>
破魂闪避：${player.dyn.antiBrokensoul}<#if player.dyn.brokensoul != 0>%</#if><br/>
破魂抵抗：${player.dyn.extraAntiBrokensoul}<br/>
迟缓闪避：${player.dyn.antiSlow}<#if player.dyn.slow != 0>%</#if><br/>
迟缓抵抗：${player.dyn.extraAntiSlow}<br/>
<@goback />
<@gofacility/>
</p></card>
</wml> 