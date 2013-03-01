<#include "/include/header.ftl">
<card title="${game_title}"><p>
月牙村赌场<br/>
赛马场种马下注<br/>
<#if mtype?exists>
你已押注
<#if mtype == '0'>
【0号】,一匹头颈高昂的好马,
</#if>
<#if mtype == '1'>
【1号】,一匹四肢强健的好马,
</#if>
<#if mtype == '2'>
【2号】,一匹肌腱发达的好马,
</#if>
<#if mtype == '3'>
【3号】,一匹四肢坚实有力的好马,
</#if>
<#if mtype == '4'>
【4号】,一匹能耐受恶劣的气候的好马,
</#if>
<#if mtype == '5'>
【5号】,一匹貌秀性温的好马,
</#if>
<#if mtype == '6'>
【6号】,一匹体质结实的好马,
</#if>
<#if mtype == '7'>
【7号】,一匹擅长跳跃的好马,
</#if>
<#if mtype == '8'>
【8号】,一匹性情温顺的好马,
</#if>
<#if mtype == '9'>
【9号】,一匹禀性灵敏的好马,
</#if>
系统将扣除了5银的赌资<br/>
</#if>
【温馨提示】：赌博有害，切勿沉迷<br/>
<@a href="/p?n_Gx/${pid}/ch/hos/c/${mtype}"/>确定</a><br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>