#set document(title: "Rendu TP QR PDF", author: "DUTILLEUL Quentin")

#set heading(numbering: "1.")
#set text(font: "Liberation Sans")
#set par(justify: true)
#set page(header: align(right)[
  _Rendu TP QR PDF, DUTILLEUL Quentin_
], number-align: bottom + right, numbering: "1")

= Nouvelles fonctionnalités

- L'utilisateur peut désormais choisir les couleurs du QR Code (fond et code en lui-même) et la couleur du texte au-dessus.
  - _Implique le rajout d'une classe permettant de stocker les préférences utilisateur (DocumentGeneratorConfig) et la création d'un nouveau composant pour la vue (ColorPicker)_

- L'utilisateur peut désormais choisir la police d'écriture du texte au-dessus du QR code
  - _Implique l'ajout d'un attribut dans DocumentGeneratorConfig ainsi que la création d'un composant pour la vue (FontPicker)_

- L'utilisateur peut désormais rajouter des images en-dessous du QR code
  - _Implique l'ajout d'un attribut dans DocumentGeneratorConfig, la modification de la méthode de génération du QR code, la création d'une nouvelle classe (AlignedImage), d'une énumération (Alignment) et le rajout de plusieurs composants dans la vue._
