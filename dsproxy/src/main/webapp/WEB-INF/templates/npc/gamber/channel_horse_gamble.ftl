<#include "/include/header.ftl">
<card title="${game_title}"><p>
月牙村赌场<br/>
赛马场：<br/>
10匹马分别是0、1、2、3、4、5、6、7、8、9、号。
每15分钟开一次奖，猜对得10倍奖金,
抽取10%手续费,
每次押注5银,单匹马每次最多下1注，可以同时购买多匹马<br/>
<#if roundHorse?exists>
上一局开奖结果为：${roundHorse}<br/>
</#if>
<#if horseList?exists>
你已下注种马：
<#list horseList as obj>
${obj},
</#list>
<br/>
距离开奖时间还有${leftHorseTime}秒, <@a href="/p?n_Gx/${pid}/ch/hos/"/>刷新</a><br/>
</#if>
请选种马下注<br/>
<@a href="/p?n_Gx/${pid}/ch/hos/g/0/"/>0号一匹头颈高昂的好马</a><br/>
<@a href="/p?n_Gx/${pid}/ch/hos/g/1/"/>1号一匹四肢强健的好马</a><br/>
<@a href="/p?n_Gx/${pid}/ch/hos/g/2/"/>2号一匹肌腱发达的好马</a><br/>
<@a href="/p?n_Gx/${pid}/ch/hos/g/3/"/>3号一匹四肢坚实有力的好马</a><br/>
<@a href="/p?n_Gx/${pid}/ch/hos/g/4/"/>4号一匹能耐受恶劣的气候的好马</a><br/>
<@a href="/p?n_Gx/${pid}/ch/hos/g/5/"/>5号一匹貌秀性温的好马</a><br/>
<@a href="/p?n_Gx/${pid}/ch/hos/g/6/"/>6号一匹体质结实的好马</a><br/>
<@a href="/p?n_Gx/${pid}/ch/hos/g/7/"/>7号一匹擅长跳跃的好马</a><br/>
<@a href="/p?n_Gx/${pid}/ch/hos/g/8/"/>8号一匹性情温顺的好马</a><br/>
<@a href="/p?n_Gx/${pid}/ch/hos/g/9/"/>9号一匹禀性灵敏的好马</a><br/>
【温馨提示】：赌博有害，切勿沉迷<br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>