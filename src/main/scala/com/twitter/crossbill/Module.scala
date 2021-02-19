package com.twitter.crossbill

// TODO: This needs additional work
case class Module(
  typeSection: TypeSection = TypeSection.Empty, 
  importSection: ImportSection = ImportSection.Empty,
  functionSection: FunctionSection = FunctionSection.Empty,
  tableSection: TableSection = TableSection.Empty,
  memorySection: MemorySection = MemorySection.Empty,
  globalSection: GlobalSection = GlobalSection.Empty,
  exportSection: ExportSection = ExportSection.Empty,
  elementSection: ElementSection = ElementSection.Empty,
  codeSection: CodeSection = CodeSection.Empty,
  dataSection: DataSection = DataSection.Empty
)
