<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<#include "/include/head_card.ftl">
${item_name}<br/>
消耗${label_item}：1<br/>
<#if (label_gold > 0 )>
消耗人民币：${label_gold}<br/>
</#if>
<#if (label_copper > 0)>
消耗游戏币：${label_copper}<br/>
</#if>
<#if label_buff_res?exists>
当前属性：<br/>
<@showBuffRands label_buff_res,label_item_id/>
</#if>
<#if label_buff_tmp?exists>
洗练后属性：<br/>
<@showBuffRandsCompare label_buff_res,label_buff_tmp/>
<@a href="/p?i_SB/${pid}//0/${label_item_id}/cm/"/>替换新属性</a><br/>
</#if>

点击锁定后就锁定您不想洗练的属性<br/>
<@a href="/p?i_SB/${pid}//0/${label_item_id}/ok/"/>继续洗练</a><br/>
自动购买洗练石继续洗练<br/>
点击锁定后就锁定原来属性，后面洗练的时候锁定属性就不变化了<br/>
<@gogame/>
<#include "/include/foot_card.ftl">