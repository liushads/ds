<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if location?exists>
${location}<br/>
</#if>
<#if lastNpc?exists>
${lastNpc.name}:${lastNpc.description}<br/>
</#if>
活动介绍<br/>
1、活动期间玩家可在月牙村-校场-五一活动-处领取一次对应等级"劳动宝箱"，打开即可获得随机装备箱一件和"宝箱宝图"一块。每4块"宝箱宝图"可前往“五一活动”处合成一个对应等级的"劳动宝箱"<br/> 
注：活动期间每位玩家仅可领取1次劳动宝箱<br/>
2、活动期间，"宝箱宝图"可以通过充值送活动，将军副本，易水寒副本，逐风副本，玩家交易等途径获取<br/>
3、"宝箱合成指引"可以通过逐风副本中怪物和商城获取<br/>
4、注：由于各区物价不同商城贩卖指引比较贵建议玩家多多考虑通过打怪获得宝箱合成指引<br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>