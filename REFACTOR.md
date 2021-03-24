# Over deze POC

Dit is een Vue 3 POC voor EMX2. Het is verplaatst naar een aparte repository,
zodat er snel wijzigingen gemaakt kunnen worden, zonder dat de bestaande
code structuur daar onder te leiden heeft. Ik moet er meteen bij zeggen
dat ik inhoudelijk niet goed kan inschatten hoe het platform werkt, maar
het valt me wel direct op dat EMX2 een stuk vlotter werkt dan EMX1/Molgenis
en dat er minder bewegende onderdelen(Elasticsearch, Minio, OpenCPU) zijn. Minder complexiteit is fijn! Ook de Graphql API lijkt me een hele nuttige
toevoeging.

Door tijdgebrek heb ik me beperkt tot het kijken naar de documentatie, de frontend en de bijbehorende tooling. Ik heb de apps structuur binnen het
project bekeken binnen dezelfde context als [molgenis-core](https://docs.google.com/document/d/1VW3ah5VAvAz2KnqNZlNmVqCzFhBMlIcjPPUlsHMFRIY).

## Mogelijke Verbeterpunten

Behalve de upgrade naar Vue 3 en Vite zijn er ook een aantal gemeenschappelijke
verbeterpunten te onderscheiden, aangezien de architectuur voor een deel gelijk
is gebleven. Hier onder een overzicht van de belangrijkste onderdelen:

### Mergen losse apps

In EMX1 is dit naar mijn idee al problematisch gebleken. Wat nu losse apps zijn,
zijn in feite onderdelen van dezelfde SPA. De ceremonie van losse
packages voor modulariteit zorgt naar mijn idee voor teveel boilerplate code
wat allemaal onderhouden moet worden. Zonder dit zouden wijzigingen sneller
kunnen worden doorgevoerd. Er hoeft niet voortdurend te worden geschakeld
tussen package a & b, het releasen van een package, het bumpen van dependencies,
dubbel installeren van npm packages, opzetten van test setups, etc.

### Vue-router

Beter benutten van vue-router; named routes gebruiken in plaats van gehardcode
paden. Prefixes voor modules gebuiken. Router links tussen verschillende app
onderdelen; geen `<a>` tags. Routing van groups zit momenteel niet goed
verwerkt in de frontend(als een prefix voor alle routing).

### Minder props

Props zijn prima voor componenten in een for loop of herbruikbare componenten,
maar het zorgt bij teveel gebruik voor moeilijk te volgen state. Een simpele
centrale store met behulp van vue `reactive` zou dit overzichtelijker kunnen
maken.

### Store toevoegen

Dit maakt een hoop dingen veel gemakkelijker; met name het tijdelijk opslaan
van state in LocalStorage en het delen van state tussen verschillende
componenten.

### Linting

Er wordt nog geen goed gebruik gemaakt van linting. Het gaat om stylelint
voor de css en eslint voor de Vue/Javascript code. Na het toevoegen van linting
vallen een aantal dingen op:

- Veel ongebruikte imports
- Geen alfabetische sortering van keys
- Redelijk wat vue warnings; die zijn momenteel uitgeschakeld in `.eslintrc.js`

### Root paden

Relatieve imports tov root(@) zijn gemakkelijker te refactoren en op te sporen.
Misschien is het daarom beter om imports die naar andere onderdelen verwijzen
(apps) te laten beginnen met de root; bijv. `import foo from '@/components/app/MyComponent.vue'`.

### Component abstractie

Ik snap de encapsulatie van bijv. een `<Molgenis>` component door de huidige
losse apps layout, maar ik weet niet of dat veel nut heeft in een SPA frontend.
De implementatie van iconen kan ook wat simpeler. Wat voor mezelf goed werkt
is dat ieder icoon een template is met daarin een svg path en dan 1 basis
implementatie van het icoon zelf. Zo kun je heel gericht iconen toevoegen
uit externe bronnen(bijv. van [deze](https://materialdesignicons.com/) site)
en eventueel aanpassen als dat nodig is.

### UX & Vormgeving

Momenteel wordt Molgenis-theme gebruikt. Daar zit nog best wel
veel legacy code in. Met een green-field applicatie zoals EMX2 is het een
aantrekkelijk idee om Bootstrap helemaal te verwijderen, en te beginnen met
vanilla CSS; bijv. PostCSS + CSS variabelen. Bootstrap is niet perse nodig
en het introduceert wat veel dependencies naar mijn idee. Mochten mensen
alsnog een framework willen gebruiken, dan is [Bulma](https://bulma.io/) een
logischere keuze, omdat het geen JavaScript dependencies kent.
Wat ook een optie is, is om elementen uit molgenis-theme in EMX2 te verwerken.
Dus een aparte build stap om Bootstrap te bouwen met een thema, en daarnaast
de CSS/SCSS styling in EMX2 die los gebouwd wordt aan de hand van dezelfde
thema configuratie.

Qua UX is het misschien fijn om minder gebruik te maken van modals en wat meer
van in-place editing waar mogelijk. Als de hoofd doelgroep voor EMX2 gebruikers
zijn met een los computer scherm (geen mobile/tablet), dan is logisch om
de menubalk naar links te verplaatsen als een sidebar. Dit past beter in de
leesrichting en maakt beter gebruik van de schermruimte. Ook kun je daarmee
gemakkelijker geneste menu-structuren maken, die van links naar rechts
te volgen zijn.

### Documentatie

Er is teveel ruis in de readme; checklistjes, todo's, ops instructies, etc.
Alles wat niet onder de eerste setup voor een buitenstaander valt, hoort
niet echt thuis in de README.md naar mijn mening. Ik las laatst een [goede
discussie](https://news.ycombinator.com/item?id=26537805) over het schrijven
van een goede readme.

### Kleine dingetjes

- Async/await ipv .then
- Logger toevoegen
- Graphql requests los van componenten halen? (soort van actions)
- Minder slots (alleen waar content variabel is); e.g. te modulair
- i18n library Vue 3 toegevoegd
- Thema niet laden uit js
- Benaming; groups / database / central?
- Graphql endpoints specifieker maken? e.g. /api/graphql/:groupId gebruiken
- Niet helemaal duidelijk waar data wordt opgehaald in TableExplorer/ListTables
