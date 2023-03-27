export default function oreToNokFormatted(ore: number): string {
  return (ore / 100).toLocaleString("no-NO", { style: "currency", currency: "NOK" });
}
