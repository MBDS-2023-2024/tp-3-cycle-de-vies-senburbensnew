[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/vRlZm8j5)
# Les activités
Dans ce TP, vous allez apprendre à gérer la navigation dans une application Android en utilisant les ```intents```

## Fonctionnalités de l'application 
Vous allez enrichir l'application, qui est une coquille vide pour l'instant. 
Les fonctionnalités à ajouter doivent permettre à un utilisateur d'effectuer les actions suivantes :
  - Effectuer une opération mathématique simple (addition, soustraction, multiplicatio, division)
  - Appel d'urgence, permettant de contacter la police
  - Effectuer une recherche sur Google
  - Ppartager un message sur les réseaux sociaux
  - Lancer un itinéraire via GMaps vers une adresse

### Tâche 1: Effecutuer une opération mathématique simple
Dans cette partie du TP, vous allez modifier créer une nouvelle activité qui permet d'effectuer une opération simple sur 2 noombres.
Pour cela, vous devrez modifier le layout principal de l'activité pour y ajouter un bouton permettant de lancer la nouvelle activité. 
Aussi, l'activité principal (MainActivity) devra indiquer le type d'opération à effectuer parmi : ADD, SUBS, DIV, MULT.

1. Ajoutez un bouton dans le layout de l'activité ```MainActivity```
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:padding="10dp"
        tools:context=".MainActivity">

  <Button
          android:id="@+id/btn_home_compute"
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:text="@string/btn_home_compute" />
  ....

</LinearLayout>
```
2. Modifier le fichier ```strings/xml``` pour y ajouter une nouvelle resource ```btn_home_compute```
> Les textes d'une application Android doivent être définis dans les ressources afin de 
> gérer l'internationalisation de l'application. En effet, quand l'application se lance, l'OS déterminera
> le texte à afficher en fonction de la langue du téléphone.

```xml
<resources>
    ...
    <string name="btn_home_compute">Faire un calcul</string>
    ...
</resources>
```

3.Créer l'activity ```ComputeActivity.kt```
- Clique droit sur le package principal ```new -> Activity -> Empty Activity```
- Entrer le nom de l'activité ```ComputeActivity```
- Changer le nom du layout ```compute_activity```
- Cliquer sur le bouton Finish 

4. Modifier le layout de la nouvelle activity et corriger les erreurs
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="10dp"
    tools:context=".ComputeActivity">

    <com.google.android.material.textfield.TextInputLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/first_operand">

        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/first_operand"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="numberDecimal" />

    </com.google.android.material.textfield.TextInputLayout>
  
    <com.google.android.material.textfield.TextInputLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="5dp"
        android:hint="@string/second_operand">

        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/second_operand"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="numberDecimal" />

    </com.google.android.material.textfield.TextInputLayout>

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:text="@string/btn_compute" />
</LinearLayout>
```

4. Intercepter le clique sur le bouton
- Ajoutez un attribut de type **Button** dans la classe **MainActivity**
```Kotlin
    private lateinit var btnHomeCompute: Button
```
> Remarquez l'utilisation du lateInit sur le bouton. On ne dispose pas de tous les éléments nécessaires pour la création du bouton, par conséquent on ne peut pas l'initialiser lors de la création de l'activité.
> Le mot clef lateInit permet d'indiquer au compilateur, qu'on le fera plus tard, c'est-à-dire à la création de la vue.

- Initialisez le bouton dans la méthode onCreate (juste après le setContentView).

```Kotlin
  ...
  private lateinit var btnCompute: Button
  ...
  override fun onCreate(savedInstanceState: Bundle?) {
    ...
    btnCompute = findViewById(R.id.btn_compute)
    ...
  }
```

> Ici on a utilisé la fonction findViewById, qui permet de récupérer l'instance d'une vue à partir de son id.
> Remarquez aussi comment on accède à l'id d'un objet ```R.id.btn_home_compute```

- Afficher l'activité ```ComputeActivity``` quand l'utilisateur clique sur le bouton
```kotlin 
  btnCompute.setOnClickListener {
      val intent = Intent(this, ComputeActivity::class.java)
      intent.extras?.putString("operation", "ADD")
      startActivity(intent)
  }
```

- Compiler et tester

5. Compléter l'activité ```ComputeActivity```

- Modifier la méthode ```onCreate``` pour récurérer le type d'opération
  ```kotlin 
    override fun onCreate(savedInstanceState: Bundle?) {
          ...
          val operation = intent.getStringExtra("operation") ?: "ADD"
    }
  ```
- Créer 3 variables pour récupérer les instances des éléments de la vue
- Quand l'utilisateur clique sur le bouton ```calculer```, effectuer l'opération sur les 2 nombres
- Ajouter un ```TextView``` sous le bouton ```calculer```
- Afficher le résultat de l'opération dans le ```TextView```

### Tâche 2: Ajouter un bouton d'appel
Dans cette partie du TP, vous allez modifier l'activité principal de l'application pour y ajouter 
un bouton 'SOS'. Quand l'utilisateur clique sur le bouton, lancer l'application ````CALL```` du téléphone
en renseignant le numéro de téléphone à appeler. 

> Pour effectuer un appel, votre application aura besoin d'une autorisation.
> Pour cela, modifier le fichier Manifest de l'application comme dans l'example ci-dessous
``` xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
package="fr.gobelins.dmi1">

    <uses-permission android:name="android.permission.CALL_PHONE" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.MainActivity">
        ...
    </application>

</manifest>
```

### Tâche 3: Faire une recherche sur Google
Dans cette partie du TP, vous allez modifier l'activité principal de l'application pour y ajouter
un bouton 'Rechercher'. Quand l'utilisateur clique sur le bouton, ouvrir le navigateur sur ```https://google.fr```. 

### Tâche 4: Partager un message sur les réseaux sociaux, par mail ou par SMS
Modifier l'acivité principale pour y ajouter un bouton qui permet à l'utilisateur de partager un contenu par mail,
sur les réseaux sociaux, par SMS etc...

### Tâche 5: Lancer un itinéraire 
Ajouter un bouton permettant à l'utilisateur de lancer un itinéraire à partir de sa position (ou un point fixe)
vers les Papeteries Gobelins.




