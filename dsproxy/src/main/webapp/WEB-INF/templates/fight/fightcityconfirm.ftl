<#include "/include/header.ftl">
<card title="${game_title}">
<p>
${location}<br/>
<@a href="/p?fc_C/${pid}/${random}"/>刷新</a><br/>
行政官：
<#if (return="0")>宣布成功，
	距离战斗开始时间还有${fight_left_time}秒，
	攻方(${tong_atk})${fight_atk_num}人，
	守方(${tong_def})${fight_def_num}人。<br/>
</#if>
<#if (return="1")>
	距离战斗开始时间还有${fight_left_time}秒，
	攻方(${tong_atk})${fight_atk_num}人，
	守方(${tong_def})${fight_def_num}人。<br/>
</#if>
<#if (return="2")>
	已经有玩家在攻城战中(攻方:${tong_atk},守方:${tong_def})<br/>
</#if>
<@goback/>
<@gofacility/>
</p>
</card>
</wml>
