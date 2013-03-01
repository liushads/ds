<#include "/include/header.ftl">
<card title="${game_title}"><p>
你是否要学习该生活技能<br/>
<#if return?number==10>
采集:学习采集可以获得制药的基本材料
</#if>
<#if return?number==11>
分解:学习分解可以获得锻造的基本材料
</#if>
<#if return?number==12>
锻造:学习锻造可以锻造出:武器、首饰、防具、暗器
</#if>
<#if return?number==13>
制药:学习制药可以制造出补充攻防敏等各种不同类型的药
</#if><br/>
学习要求<br/>
金钱:10银币<br/>
等级:10级<br/>
<@a href="/p?se_XL/${pid}/${sect_id}/${num}/${return}"/>学习</a><br/>
<@goback/>
<@gogame/>
</p></card>
</wml>