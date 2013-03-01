<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
【闭关修炼提示】<br/>
您当前等级每次闭关修炼每小时获得:人物${unitExp}经验，宠物${unitExp}经验<br/>
累积离线经验达到1小时才可领取<br/>
每天最多可修炼5小时，满后会自动停止修炼<br/>
每日可免费领取2个闭关修炼符<br/>
剩于3小时若不领取则进入累积经验中<br/>
<@goback/>
<@gogame/>
</p></card>
</wml>